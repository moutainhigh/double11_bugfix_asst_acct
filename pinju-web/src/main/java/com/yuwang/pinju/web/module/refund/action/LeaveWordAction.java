package com.yuwang.pinju.web.module.refund.action;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.yuwang.pinju.common.FileSecurityUtils;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.refund.ao.RefundApplyAO;
import com.yuwang.pinju.core.refund.ao.TradeRefundAO;
import com.yuwang.pinju.domain.refund.RefundDO;
import com.yuwang.pinju.domain.refund.TradeRefundLeavewordDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.message.RefundMessage;

public class LeaveWordAction extends RefundBaseAction{
	private static final long serialVersionUID = 9062503068777721608L;
	private Long refundId;

	private RefundApplyAO refundApplyAO;
	private TradeRefundAO tradeRefundAO;
	
	private File[] voucherPic;
    private String[] voucherPicFileName;
    private String[] voucherPicContentType;
    
    private Long userType;

    private String texLeave;
    
    private String tipRefundUrl;
    
    private RefundDO refundDO;
    private String pageUrl;
    /**
     * 发表留言
     */
	public String execute(){
		PrintWriter out=null;
		TradeRefundLeavewordDO tdo = new TradeRefundLeavewordDO();
		if (log.isInfoEnabled()) {
			log.info("execute RefundCheckApplyAction.saveLeaveWord() begin");
		}
		try{
			tdo.setMemberId(CookieLoginInfo.getCookieLoginInfo().getMemberId());
			tdo.setRefundId(refundId);
			tdo.setMemberNick(CookieLoginInfo.getCookieLoginInfo().getNickname());
			tdo.setContent(texLeave);
			tdo.setUserType(userType);
			HttpServletResponse response = ServletActionContext.getResponse();
			out = response.getWriter();
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			if(StringUtils.isNotBlank(pageUrl)){
				if(pageUrl.indexOf("#") != -1){
					pageUrl=pageUrl.substring(0, pageUrl.indexOf("#"));
				}
			}
			if(!verifyPics(tdo)){
				out.println("<script>window.parent.callbackLeaveWord('"+pageUrl+"','"+this.getActionErrors().toArray()[0]+"')</script>");
				return null;
			}
			out.println("<script>window.parent.callbackLeaveWord('"+pageUrl+"','')</script>");
			tradeRefundAO.insertRefundLeaveword(tdo);     
		}catch (Exception e) {
			out.println("<script>window.parent.callbackLeaveWord('"+pageUrl+"','"+RefundMessage.getMessage(OPERATE_FAILED)+"')</script>");
			log.error("Entry: RefundCheckApplyAction.saveLeaveWord() error occurs: "+e);
			return null;
		}
		return null;
	}
	
	/**
	 * <p>Description: 处理图片</p>
	 * @param loginInfo
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-10-22
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private boolean verifyPics(TradeRefundLeavewordDO tdo){
		try {
			Long memberId = CookieLoginInfo.getCookieLoginInfo().getMemberId();
			String nickName = CookieLoginInfo.getCookieLoginInfo().getNickname();
			if (voucherPic != null && voucherPic.length > 0) {
				for (File file : voucherPic) {
					if (file.length() / PinjuConstant.FILE_SIZE_K > FILE_MAX_SIZE) {
						addActionError(RefundMessage.getMessage(
								FILE_SIZE_TO_LARGE, FILE_MAX_SIZE));
						return false;
					}
					
					if (!FileSecurityUtils.isImageValid(file)) {
						addActionError(RefundMessage.getMessage(FILE_TYPE_INVALID));
						return false;
					}
				}
				Result result = tradeRefundAO.saveFile(voucherPic,
						voucherPicFileName, memberId, nickName);
				if (result.isSuccess()) {
					String[] retFileName = (String[]) result
							.getModel("fileNames");
					if (retFileName.length == 1) {
						tdo.setPic1(retFileName[0]);
					} else if (retFileName.length == 2) {
						tdo.setPic1(retFileName[0]);
						tdo.setPic2(retFileName[1]);
					} else if (retFileName.length == 3) {
						tdo.setPic1(retFileName[0]);
						tdo.setPic2(retFileName[1]);
						tdo.setPic3(retFileName[2]);
					}
				}
			}
		} catch (Exception e) {
			addActionError(e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * <p>Description:  测试图片异步验证 </p>
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-8-31
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String validateUploadImage() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			if(voucherPic != null && voucherPic.length > 0) {
				JSONObject m = new JSONObject();
				for (File file : voucherPic) {
					if (file.length()/PinjuConstant.FILE_SIZE_K > FILE_MAX_SIZE) {
						m.put("imagFlag",RefundMessage.getMessage(FILE_SIZE_TO_LARGE,FILE_MAX_SIZE));
						out.print(m.toString());
						return null;
					}
					
					if (!FileSecurityUtils.isImageValid(file)) {
						m.put("imagFlag", RefundMessage.getMessage(FILE_TYPE_INVALID));
						out.print(m.toString());
						return null;
					}
				}
				m.put("imagFlag","1");
				out.print(m.toString());
			}
		} catch (Exception e) {
			log.error("LeaveWordAction.validateUploadImage()", e);
		}
		return null;
	}
	
	/**
	 * <p>Description: 	 先留言后拒绝退款 </p>
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-9-6
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String leaveWordForSell(){
		TradeRefundLeavewordDO tdo = new TradeRefundLeavewordDO();
		if (log.isInfoEnabled()) {
			log.info("Entry: RefundCheckApplyAction.saveLeaveWord()");
		}
		try{
			 refundDO = refundApplyAO.loadRefundApplyInfo(refundId);
			if(refundDO.getRefundState().compareTo(RefundDO.STATUS_WAIT_SELLER_AGREE) != 0){
				this.sellerTipMessage(refundDO, REFUND_DEFAULT_MESSAGE);
				return INPUT;
			}
			tdo.setMemberId(CookieLoginInfo.getCookieLoginInfo().getMemberId());
			tdo.setRefundId(refundId);
			tdo.setMemberNick(CookieLoginInfo.getCookieLoginInfo().getNickname());
			tdo.setContent(texLeave);
			tdo.setUserType(userType);
			tradeRefundAO.insertRefundLeaveword(tdo);
		}catch (Exception e) {
			log.error("Entry: RefundCheckApplyAction.saveLeaveWord() error occurs: "+e);
		}
		return SUCCESS;
	}
	
	
	
	public String getPageUrl() {
		return pageUrl;
	}
	
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	
	public Long getRefundId() {
		return refundId;
	}

	public void setRefundId(Long refundId) {
		this.refundId = refundId;
	}

	public RefundApplyAO getRefundApplyAO() {
		return refundApplyAO;
	}

	public void setRefundApplyAO(RefundApplyAO refundApplyAO) {
		this.refundApplyAO = refundApplyAO;
	}

	public TradeRefundAO getTradeRefundAO() {
		return tradeRefundAO;
	}

	public void setTradeRefundAO(TradeRefundAO tradeRefundAO) {
		this.tradeRefundAO = tradeRefundAO;
	}

	public File[] getVoucherPic() {
		return voucherPic;
	}

	public void setVoucherPic(File[] voucherPic) {
		this.voucherPic = voucherPic;
	}

	public String[] getVoucherPicFileName() {
		return voucherPicFileName;
	}

	public void setVoucherPicFileName(String[] voucherPicFileName) {
		this.voucherPicFileName = voucherPicFileName;
	}

	public String[] getVoucherPicContentType() {
		return voucherPicContentType;
	}

	public void setVoucherPicContentType(String[] voucherPicContentType) {
		this.voucherPicContentType = voucherPicContentType;
	}

	public Long getUserType() {
		return userType;
	}

	public void setUserType(Long userType) {
		this.userType = userType;
	}

	public String getTexLeave() {
		return texLeave;
	}

	public void setTexLeave(String texLeave) {
		this.texLeave = texLeave;
	}

	public String getTipRefundUrl() {
		return tipRefundUrl;
	}

	public void setTipRefundUrl(String tipRefundUrl) {
		this.tipRefundUrl = tipRefundUrl;
	}

	public RefundDO getRefundDO() {
		return refundDO;
	}

	public void setRefundDO(RefundDO refundDO) {
		this.refundDO = refundDO;
	}
}
