/*
 * Copyright (c) 2010-2014 Osman Shoukry
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License or any
 *    later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.openpojo.registry;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.random.collection.list.ListConcreteRandomGenerator;
import com.openpojo.random.collection.queue.QueueConcreteRandomGenerator;
import com.openpojo.random.collection.set.SetConcreteRandomGenerator;
import com.openpojo.random.impl.*;
import com.openpojo.random.map.MapConcreteRandomGenerator;
import com.openpojo.random.service.RandomGeneratorService;
import com.openpojo.random.service.impl.DefaultRandomGeneratorService;
import com.openpojo.reflection.adapt.impl.CloverPojoClassAdapter;
import com.openpojo.reflection.adapt.impl.CoberturaPojoClassAdapter;
import com.openpojo.reflection.adapt.impl.JacocoPojoClassAdapter;
import com.openpojo.reflection.adapt.service.PojoClassAdaptationService;
import com.openpojo.reflection.adapt.service.impl.DefaultPojoClassAdaptationService;
import com.openpojo.reflection.service.PojoClassLookupService;
import com.openpojo.reflection.service.impl.DefaultPojoClassLookupService;

/**
 * @author oshoukry
 */
public class ServiceRegistrar {
    private RandomGeneratorService randomGeneratorService;
    private PojoClassLookupService pojoClassLookupService;
    private PojoClassAdaptationService pojoClassAdaptationService;

    private ServiceRegistrar() {
        initializePojoClassLookupService();
        initializePojoClassAdaptationService();
        initializeRandomGeneratorService();
    }

    public void initializePojoClassAdaptationService() {
        final PojoClassAdaptationService newPojoClassAdaptationService = new DefaultPojoClassAdaptationService();

        // TODO: Instrumentation of Fields by coverage tools needs to be refactored out into its own service.
        newPojoClassAdaptationService.registerPojoClassAdapter(JacocoPojoClassAdapter.getInstance());

        try {
            if (Class.forName("net.sourceforge.cobertura.coveragedata.LightClassmapListener") != null) {
                System.out.println("Cobertura instrumentation detected, registering Cobertura class members filter");
                newPojoClassAdaptationService.registerPojoClassAdapter(CoberturaPojoClassAdapter.getInstance());
            }
        } catch (ClassNotFoundException ignored) {
        }

        try {
            if (Class.forName("com_cenqua_clover.TestNameSniffer") != null) {
                System.out.println("Clover 3 instrumentation detected, registering Clover class member filter");
                newPojoClassAdaptationService.registerPojoClassAdapter(CloverPojoClassAdapter.getInstance());
            }
        } catch (ClassNotFoundException ignored) {
        }

        try {
            if (Class.forName("com_atlassian_clover.TestNameSniffer") != null) {
                System.out.println("Clover 4 instrumentation detected, registering Clover class member filter");
                newPojoClassAdaptationService.registerPojoClassAdapter(CloverPojoClassAdapter.getInstance());

            }
        } catch (ClassNotFoundException ignored) {
        }

        setPojoClassAdaptationService(newPojoClassAdaptationService);
    }

    public void initializeRandomGeneratorService() {

        final RandomGeneratorService newRandomGeneratorService = new DefaultRandomGeneratorService();

        // TODO: This code needs to move out of the registrar.
        // Default Generator
        newRandomGeneratorService.setDefaultRandomGenerator(new DefaultRandomGenerator());

        // register basic types.
        newRandomGeneratorService.registerRandomGenerator(VoidRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(ObjectRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(ClassRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(BasicRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(TimestampRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(EnumRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(EnumSetRandomGenerator.getInstance());

        // Lists
        newRandomGeneratorService.registerRandomGenerator(ListConcreteRandomGenerator.getInstance());

        // Sets
        newRandomGeneratorService.registerRandomGenerator(SetConcreteRandomGenerator.getInstance());

        // Queue
        newRandomGeneratorService.registerRandomGenerator(QueueConcreteRandomGenerator.getInstance());

        // Map
        newRandomGeneratorService.registerRandomGenerator(MapConcreteRandomGenerator.getInstance());

        setRandomGeneratorService(newRandomGeneratorService);

    }

    public void initializePojoClassLookupService() {
        pojoClassLookupService = new DefaultPojoClassLookupService();
    }

    public static ServiceRegistrar getInstance() {
        return Instance.INSTANCE;
    }

    public void setRandomGeneratorService(final RandomGeneratorService randomGeneratorService) {
        this.randomGeneratorService = randomGeneratorService;
    }

    public RandomGeneratorService getRandomGeneratorService() {
        return randomGeneratorService;
    }

    public PojoClassLookupService getPojoClassLookupService() {
        return pojoClassLookupService;
    }

    public void setPojoClassAdaptationService(final PojoClassAdaptationService pojoClassAdaptationService) {
        this.pojoClassAdaptationService = pojoClassAdaptationService;
    }

    public PojoClassAdaptationService getPojoClassAdaptationService() {
        return pojoClassAdaptationService;
    }

    @Override
    public String toString() {
        return BusinessIdentity.toString(this);
    }

    private static class Instance {
        private static final ServiceRegistrar INSTANCE = new ServiceRegistrar();
    }

}
