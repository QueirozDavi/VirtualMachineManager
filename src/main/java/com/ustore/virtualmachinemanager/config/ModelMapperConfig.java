package com.ustore.virtualmachinemanager.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

//    private static final long CONVERT_NUMBER = 1073741824;

//    @Bean
//    public ModelMapper getModelMapper() {
//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.addConverter(getMapCpuInfoMapper(), ThreadInfo.class, CpuInfo.class);
//        return modelMapper;
//    }
//
//    private Converter<ThreadInfo, CpuInfo> getMapCpuInfoMapper() {
//        return context -> {
//
//            ThreadInfo source = context.getSource();
//            CpuInfo cpuInfo = new CpuInfo();
//            cpuInfo.setThreadName(source.getThreadName());
//            cpuInfo.setThreadState(source.getThreadState().name());
//
//            return cpuInfo;
//        };
//    }



}
