package ru.smartel.strike.validation;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistsValidator implements ConstraintValidator<Exists, String> {
    @Autowired
    EntityManagerFactory entityManagerFactory;

    private Class entity;
    private String field;

    @Override
    public void initialize(Exists constraintAnnotation) {
        entity = constraintAnnotation.entity();
        field = constraintAnnotation.field();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            return !em
                    .createQuery("select e.id from " + entity.getName() + " e where " + field + " = \'" + value + "\'")
                    .setMaxResults(1)
                    .getResultList().isEmpty();
        } catch (Exception ex) {
            return false;
        } finally {
            em.close();
        }
    }
}
