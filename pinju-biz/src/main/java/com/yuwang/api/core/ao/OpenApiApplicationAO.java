package com.yuwang.api.core.ao;

import java.util.Map;

import com.yuwang.api.domain.OpenApiApplicationDO;

public interface OpenApiApplicationAO {
   public Map<Long,OpenApiApplicationDO> getAllOpenApiApplication();
}
