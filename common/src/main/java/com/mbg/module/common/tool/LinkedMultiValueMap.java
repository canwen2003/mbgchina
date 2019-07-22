package com.mbg.module.common.tool;

import java.util.LinkedHashMap;

public class LinkedMultiValueMap extends BasicMultiValueMap {
    public LinkedMultiValueMap() {
        super(new LinkedHashMap());
    }
}
