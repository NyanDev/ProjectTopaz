package com.nyandev.projecttopaz.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by xuan- on 03/03/2018.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerFragment {
}
