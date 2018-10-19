package com.maxrocky.gundam.common.factory;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * Created by Tom on 2016/5/27 19:04.
 * Describe:Init MapperFactory.
 */
@Component
public class MapperFacadeFactory implements FactoryBean<MapperFacade> {

    public MapperFacade getObject() throws Exception {
        return new DefaultMapperFactory.Builder().build().getMapperFacade();
    }

    public Class<?> getObjectType() {
        return MapperFacade.class;
    }

    public boolean isSingleton() {
        return true;
    }

}
