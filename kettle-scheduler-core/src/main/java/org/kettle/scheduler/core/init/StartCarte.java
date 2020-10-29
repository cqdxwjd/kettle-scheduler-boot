package org.kettle.scheduler.core.init;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({CarteInit.class})
public @interface StartCarte {

}
