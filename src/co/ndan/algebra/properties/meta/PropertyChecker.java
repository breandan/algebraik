package co.ndan.algebra.properties.meta;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import co.ndan.algebra.properties.meta.annotation.MagicProperty;

@SupportedAnnotationTypes("co.ndan.algebra.properties.meta.annotation.MagicProperty")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class PropertyChecker
        extends AbstractProcessor {
    private ProcessingUtils util;


    @Override
    public void init(ProcessingEnvironment pe) {
        super.init(pe);
        this.util = new ProcessingUtils(processingEnv);
    }


    @Override
    public boolean process(Set<? extends TypeElement> _
            , RoundEnvironment roundEnv
    ) {
        util.note("PropertyChecker.process running.");

        for (Element e : roundEnv.getElementsAnnotatedWith(MagicProperty.class)) {
            BadPropertyException log = new BadPropertyException(null);

            try {
                new PropertyDefn(util
                        , (TypeElement) e
                        , log
                );
            } catch (final BadPropertyException exc) {
                // do nothing
            }

            log.dumpInto(processingEnv.getMessager());
        }

        return true;
    }

}
