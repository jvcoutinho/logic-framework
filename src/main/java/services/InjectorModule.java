package services;

import com.google.inject.AbstractModule;
import interfaces.*;

public class InjectorModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Generator.class).to(AnalyticTableauxGenerator.class);
        bind(Solver.class).to(AnalyticTableauxSolver.class);
        bind(Verifier.class).to(AnalyticTableauxVerifier.class);
    }

}