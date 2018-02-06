package com.thoughtworks.whatyourward.injection.component;

import dagger.Subcomponent;
import com.thoughtworks.whatyourward.injection.PerFragment;
import com.thoughtworks.whatyourward.injection.module.FragmentModule;

/**
 * This component inject dependencies to all Fragments across the application
 */
@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {
}
