package ru.smartel.strike.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.Event;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Events near to point
 * Haversine formula
 * 6371 * acos(cos(radians(:lat)) * cos(radians(e.latitude)) * cos(radians(e.longitude) - radians(:lng)) + sin(radians(:lat)) * sin(radians(e.latitude))) <= :radius
 * https://stackoverflow.com/questions/21084886/how-to-calculate-distance-using-latitude-and-longitude
 */
public class NearCoordinateEvent implements Specification<Event> {
    private Double latitude;
    private Double longitude;
    private Double radius;

    public NearCoordinateEvent(Double latitude, Double longitude, Double radius) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.lessThanOrEqualTo(
                cb.prod(6371D,
                        cb.function(
                                "ACOS",
                                Double.class,
                                cb.sum(
                                        cb.prod(
                                                cb.function(
                                                        "COS",
                                                        Double.class,
                                                        cb.function("RADIANS", Double.class, cb.literal(latitude))
                                                ),
                                                cb.prod(
                                                        cb.function(
                                                                "COS",
                                                                Double.class,
                                                                cb.function("RADIANS", Double.class, root.get("latitude"))
                                                        ),
                                                        cb.function(
                                                                "COS",
                                                                Double.class,
                                                                cb.diff(
                                                                        cb.function("RADIANS", Double.class, root.get("longitude")),
                                                                        cb.function("RADIANS", Double.class, cb.literal(longitude))
                                                                )
                                                        )
                                                )
                                        ),
                                        cb.prod(
                                                cb.function(
                                                        "SIN",
                                                        Double.class,
                                                        cb.function("RADIANS", Double.class, cb.literal(latitude))
                                                ),
                                                cb.function(
                                                        "SIN",
                                                        Double.class,
                                                        cb.function("RADIANS", Double.class, root.get("latitude"))
                                                )
                                        )
                                )

                        )
                ),
                radius
        );
    }
}
