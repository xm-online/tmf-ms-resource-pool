package com.icthh.xm.tmf.ms.resourcepool;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.icthh.xm.tmf.ms.resourcepool");

        noClasses()
            .that()
                .resideInAnyPackage("com.icthh.xm.tmf.ms.resourcepool.service..")
            .or()
                .resideInAnyPackage("com.icthh.xm.tmf.ms.resourcepool.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.icthh.xm.tmf.ms.resourcepool.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
