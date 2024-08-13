package ru.gb.aspect.recover;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties("application.recover")
public class RecoverProperties {
    private Boolean enabled = true;
    private List<Class<?>> noRecoverFor = new ArrayList<>();
}
