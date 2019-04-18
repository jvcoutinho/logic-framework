package interfaces;

import com.google.inject.AbstractModule;

public class InjectorModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Generator.class).to(Generator.class);
        bind(Solver.class).to(Solver.class);
        bind(Verifier.class).to(Verifier.class);
    }

}