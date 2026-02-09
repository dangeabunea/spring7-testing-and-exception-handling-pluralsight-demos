package com.pluralsight.afm.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// @Target(ElementType.TYPE) - This annotation can only be applied to classes/records (not fields),
//   because it needs access to multiple fields to compare departure and destination.
// @Retention(RetentionPolicy.RUNTIME) - The annotation is available at runtime,
//   so the validation framework can read it via reflection.
// @Constraint(validatedBy = ...) - Links this annotation to the FlightRouteValidator class
//   that contains the actual validation logic.
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FlightRouteValidator.class)
public @interface ValidFlightRoute {
    // These three methods are required by the Bean Validation specification for every constraint annotation.
    String message() default "Departure and destination cities must be different";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
