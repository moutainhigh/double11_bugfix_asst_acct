package com.yuwang.pinju.domain.member;

import com.yuwang.pinju.common.RelationEntity;

public class MemberSequenceDO implements RelationEntity<Integer> {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private Long nextSeq;
	private Integer seqStep;

	public MemberSequenceDO() {
	}

	@Override
	public boolean isNew() {
		return (id != null);
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getNextSeq() {
		return nextSeq;
	}
	public void setNextSeq(Long nextSeq) {
		this.nextSeq = nextSeq;
	}
	public Integer getSeqStep() {
		return seqStep;
	}
	public void setSeqStep(Integer seqStep) {
		this.seqStep = seqStep;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((nextSeq == null) ? 0 : nextSeq.hashCode());
		result = prime * result + ((seqStep == null) ? 0 : seqStep.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MemberSequenceDO other = (MemberSequenceDO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nextSeq == null) {
			if (other.nextSeq != null)
				return false;
		} else if (!nextSeq.equals(other.nextSeq))
			return false;
		if (seqStep == null) {
			if (other.seqStep != null)
				return false;
		} else if (!seqStep.equals(other.seqStep))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SequenceDO [id=" + id + ", name=" + name + ", nextSeq=" + nextSeq + ", seqStep=" + seqStep + "]";
	}
}
