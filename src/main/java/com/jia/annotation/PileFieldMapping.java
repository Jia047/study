package com.jia.annotation;

import lombok.Data;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @Author: kk
 * @Date: Created in 2019/9/12 23:46
 */
@Data
public class PileFieldMapping {

    /**
     * 桩ID
     */
    @FieldName(name = "桩ID")
    private String pileId;


    /**
     * 接入商ID
     */
    @FieldName(name = "接入商ID")
    private Integer providerId;

    /**
     * 出厂编码/设备ID
     */
    @FieldName(name = "出厂编码/设备ID")
    private String deviceId;

    /**
     * 运行编码
     */
    @FieldName(name = "运行编码")
    private String pileCode;

    /**
     * 桩名称
     */
    @FieldName(name = "桩名称")
    private String pileName;

    private static class FieldMap{
        private static final Map<String, String> PILE_FIELD_NAME_MAP;
        static{
            Field[] fields = PileFieldMapping.class.getDeclaredFields();
            PILE_FIELD_NAME_MAP = new HashMap<>(fields.length);
            for(Field f : fields){
                FieldName anno = f.getAnnotation(FieldName.class);
                PILE_FIELD_NAME_MAP.put(f.getName() ,anno.name());
            }

        }
    }

    public static Map<String, String> getFieldMap(){
        return FieldMap.PILE_FIELD_NAME_MAP;
    }

    public static void main(String[] args) {
        System.out.println(123);
        System.out.println(getFieldMap());
    }
}