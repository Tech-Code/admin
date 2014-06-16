package com.techapi.bus.annotation;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.SerializerFactory;

public class HessianSerializer implements ISerializer{
	private static final Log log = LogFactory.getLog(HessianSerializer.class);
	private static final SerializerFactory _serializerFactory = new SerializerFactory();
    public byte[] encode(Object o) {
    	if(o == null){
    		return null;
    	}
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Hessian2Output out = new Hessian2Output(bos);
        out.setSerializerFactory(_serializerFactory); 
        try {
            out.writeObject(o);
            out.flush();

            byte[] bytes = bos.toByteArray();
            return bytes;
        } catch (Exception e) {
        	log.error(e.getMessage(), e);
        }
        return null;
    }

    public Object decode(byte[] bArr) {
    	if(bArr == null){
    		return null;
    	}
        try {
        	
            ByteArrayInputStream bin = new ByteArrayInputStream(bArr);
            Hessian2Input in = new Hessian2Input(bin);
            in.setSerializerFactory(_serializerFactory); //由于程序会blocked，加上这行试试
            Object o = (Object) in.readObject(Object.class);
            return o;
        } catch (Exception e) {
        	log.error(e.getMessage(), e);
        }
        return null;
    }
    
}
