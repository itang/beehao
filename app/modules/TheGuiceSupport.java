package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import play.modules.guice.GuiceSupport;


/**
 * TheGuiceSupport.
 *
 * @author itang
 */
public class TheGuiceSupport extends GuiceSupport {

    @Override
    protected Injector configure() {
        return Guice.createInjector(new AbstractModule() {

            @Override
            protected void configure() {
                //TODO
            }
        });
    }
}
