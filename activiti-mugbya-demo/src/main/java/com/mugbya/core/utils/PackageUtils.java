package com.mugbya.core.utils;

import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author mugbya
 * @version 2014-08-20.
 */
public class PackageUtils {
    private final static Logger log = Logger.getLogger(PackageUtils.class);

    static public List<String> getPackages(String packageString) {
        List<String> packages = new ArrayList<String>();;
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
        String[] packagePath = packageString.split("[,;]");
        for(String str : packagePath) {
            String path = str.trim();
            Resource[] resources = null;
            path = ClassUtils.convertClassNameToResourcePath(path) + "/*.class";
            if(!path.startsWith(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX)) {
                path = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + path;
            }
            try {
                resources = resolver.getResources(path);
            } catch (IOException e) {
            }
            if(resources == null) {
                continue;
            }

            for(Resource resource : resources) {
                try {
                    if (resource.isReadable()) {
                        MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                        String className = metadataReader.getClassMetadata().getClassName();

                        int dotIdx = className.lastIndexOf(".");
                        if(dotIdx == -1) {
                            continue;
                        }
                        String classPackageString = className.substring(0, dotIdx);

                        packages.add(classPackageString);
                    }
                } catch (IOException e) {
                }
            }
        }
        return packages;
    }

    static public void main(String[] args) {
        String className = "sadflj.asdflkj11.cccc";
        int dotIdx = className.lastIndexOf(".");
        String packageString = className.substring(0, dotIdx);
        System.out.println(packageString);
    }
}
