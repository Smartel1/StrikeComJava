package ru.smartel.strike.repository.conflict;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(rollbackFor = Exception.class)
@SuppressWarnings("unchecked")
public class ReportsConflictRepositoryImpl implements ReportsConflictRepository {
    public static final String NOT_SPECIFIED = "Не указано";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long getOldConflictsCount(LocalDate from, LocalDate to) {
        return ((BigInteger) entityManager.createNativeQuery(
                "select count(distinct(c.id))" +
                        " from conflicts c" +
                        " left join events e on e.conflict_id = c.id" +
                        " where e.date >= :from and e.date <= :to" +
                        " and c.date_from < :from")
                .setParameter("from", from)
                .setParameter("to", to)
                .getSingleResult()).longValue();
    }

    @Override
    public Map<String, Integer> getCountByCountries(LocalDate from, LocalDate to) {
        // conflict's country is the country of the first conflict's event (first by date)
        List<Object[]> resultList = entityManager.createNativeQuery(
                "with sub as (" +
                        "    select e.conflict_id," +
                        "           e.locality_id," +
                        "           ROW_NUMBER() OVER (PARTITION BY e.conflict_id ORDER BY e.date) AS rk" +
                        "    from events e where e.date >= :from and e.date <= :to)" +
                        " select c.name_ru, count(conflict_id)" +
                        " from sub" +
                        "         left join localities l on l.id = sub.locality_id" +
                        "         left join regions r on r.id = l.region_id" +
                        "         left join countries c on c.id = r.country_id" +
                        " where rk = 1" +
                        " group by c.name_ru")
                .setParameter("from", from)
                .setParameter("to", to)
                .getResultList();

        return resultList.stream()
                .collect(Collectors.toMap(r -> mapKey(r[0]), r -> ((BigInteger) r[1]).intValue()));
    }

    @Override
    public Map<String, Integer> getCountByDistricts(LocalDate from, LocalDate to) {
        // conflict's district is the district of the first conflict's event (first by date)
        List<Object[]> resultList = entityManager.createNativeQuery(
                "with sub as (" +
                        "    select e.conflict_id," +
                        "           e.locality_id," +
                        "           ROW_NUMBER() OVER (PARTITION BY e.conflict_id ORDER BY e.date) AS rk" +
                        "    from events e where e.date >= :from and e.date <= :to)" +
                        " select d.name, count(conflict_id)" +
                        " from sub" +
                        "         left join localities l on l.id = sub.locality_id" +
                        "         left join regions r on r.id = l.region_id" +
                        "         left join districts d on d.id = r.district_id" +
                        " where rk = 1" +
                        " group by d.name")
                .setParameter("from", from)
                .setParameter("to", to)
                .getResultList();

        return resultList.stream()
                .collect(Collectors.toMap(r -> mapKey(r[0]), r -> ((BigInteger) r[1]).intValue()));
    }

    @Override
    public Map<String, Float> getSpecificCountByDistricts(LocalDate from, LocalDate to) {
        // conflict's district is the district of the first conflict's event (first by date)
        List<Object[]> resultList = entityManager.createNativeQuery(
                "with sub as (" +
                        "    select e.conflict_id," +
                        "           e.locality_id," +
                        "           ROW_NUMBER() OVER (PARTITION BY e.conflict_id ORDER BY e.date) AS rk" +
                        "    from events e where e.date >= :from and e.date <= :to)" +
                        " select d.name, " +
                        "        cast (count(conflict_id) * 1000000 as float) / cast ((select population from districts where districts.name = d.name limit 1) as float)" +
                        " from sub" +
                        "         left join localities l on l.id = sub.locality_id" +
                        "         left join regions r on r.id = l.region_id" +
                        "         left join districts d on d.id = r.district_id" +
                        " where rk = 1" +
                        " group by d.name" +
                        " having d.name is not null")
                .setParameter("from", from)
                .setParameter("to", to)
                .getResultList();

        return resultList.stream()
                .collect(Collectors.toMap(r -> mapKey(r[0]), r -> ((Double) r[1]).floatValue()));
    }

    @Override
    public Map<String, Integer> getCountByIndustries(LocalDate from, LocalDate to) {
        List<Object[]> resultList = entityManager.createNativeQuery(
                "select i.name_ru, count(distinct(c.id))" +
                        " from conflicts c" +
                        " left join industries i on i.id = c.industry_id" +
                        " left join events e on e.conflict_id = c.id" +
                        " where e.date >= :from and e.date <= :to" +
                        " group by i.name_ru")
                .setParameter("from", from)
                .setParameter("to", to)
                .getResultList();

        return resultList.stream()
                .collect(Collectors.toMap(r -> mapKey(r[0]), r -> ((BigInteger) r[1]).intValue()));
    }

    @Override
    public Map<String, Integer> getCountByReasons(LocalDate from, LocalDate to) {
        List<Object[]> resultList = entityManager.createNativeQuery(
                "select cr.name_ru, count(distinct(c.id))" +
                        " from conflicts c" +
                        " left join conflict_reasons cr on cr.id = c.conflict_reason_id" +
                        " left join events e on e.conflict_id = c.id" +
                        " where e.date >= :from and e.date <= :to" +
                        " group by cr.name_ru")
                .setParameter("from", from)
                .setParameter("to", to)
                .getResultList();

        return resultList.stream()
                .collect(Collectors.toMap(r -> mapKey(r[0]), r -> ((BigInteger) r[1]).intValue()));
    }

    @Override
    public Map<String, Integer> getCountByResults(LocalDate from, LocalDate to) {
        List<Object[]> resultList = entityManager.createNativeQuery(
                "select cr.name_ru, count(distinct(c.id))" +
                        " from conflicts c" +
                        " left join conflict_results cr on cr.id = c.conflict_result_id" +
                        " left join events e on e.conflict_id = c.id" +
                        " where e.date >= :from and e.date <= :to" +
                        " group by cr.name_ru")
                .setParameter("from", from)
                .setParameter("to", to)
                .getResultList();

        return resultList.stream()
                .collect(Collectors.toMap(r -> mapKey(r[0]), r -> ((BigInteger) r[1]).intValue()));
    }

    @Override
    public Map<String, Integer> getCountByTypes(LocalDate from, LocalDate to) {
        List<Object[]> resultList = entityManager.createNativeQuery(
                "select et.name_ru, count(distinct(c.id))" +
                        " from conflicts c" +
                        " left join event_types et on et.id = c.main_type_id" +
                        " left join events e on e.conflict_id = c.id" +
                        " where e.date >= :from and e.date <= :to" +
                        " group by et.name_ru")
                .setParameter("from", from)
                .setParameter("to", to)
                .getResultList();

        return resultList.stream()
                .collect(Collectors.toMap(r -> mapKey(r[0]), r -> ((BigInteger) r[1]).intValue()));
    }

    @Override
    public Map<String, Map<String, Float>> getCountPercentByResultsByTypes(LocalDate from, LocalDate to) {
        var types = (List<Object[]>) entityManager.createNativeQuery(
                "select name_ru, id from event_types")
                .getResultList();
        return types.stream().collect(Collectors.toMap(
                raw -> (String) raw[0], // name of conflict main type
                raw -> {
                    var countToResult = ((List<Object[]>) entityManager.createNativeQuery(
                            "select cr.name_ru, sub.count" +
                                    " from conflict_results cr" +
                                    "   full outer join (select c.conflict_result_id res_id, count(distinct(c.id))" +
                                    "                   from conflicts c" +
                                    "                   left join events e on e.conflict_id = c.id" +
                                    "                   where e.date >= :from and e.date <= :to" +
                                    "                   and c.main_type_id = :mainTypeId" +
                                    "                   group by c.conflict_result_id) sub on sub.res_id = cr.id")
                            .setParameter("mainTypeId", raw[1])
                            .setParameter("from", from)
                            .setParameter("to", to)
                            .getResultList())
                            .stream().collect(Collectors.toMap(
                                    raw1 -> mapKey(raw1[0]), // name of conflict result
                                    raw1 -> Optional.ofNullable(raw1[1])
                                            .map(count -> ((BigInteger) count).intValue())
                                            .orElse(0) // count of conflicts
                            ));
                    // to always return not specified count
                    countToResult.putIfAbsent(NOT_SPECIFIED, 0);
                    int totalConflictOfType = countToResult.values().stream().reduce(Integer::sum).orElse(0);
                    var countPercentToResult = new HashMap<String, Float>();
                    countToResult.forEach((key, value) -> countPercentToResult.put(key, value == 0 ? 0 : value.floatValue() * 100 / totalConflictOfType));
                    return countPercentToResult;
                }));
    }

    @Override
    public Map<String, Map<String, Float>> getCountPercentByResultsByIndustries(LocalDate from, LocalDate to) {
        var industries = (List<Object[]>) entityManager.createNativeQuery(
                "select name_ru, id from industries")
                .getResultList();
        return industries.stream().collect(Collectors.toMap(
                raw -> (String) raw[0], // name of conflict main type
                raw -> {
                    var countToResult = ((List<Object[]>) entityManager.createNativeQuery(
                            "select cr.name_ru, sub.count" +
                                    " from conflict_results cr" +
                                    "   full outer join (select c.conflict_result_id res_id, count(distinct(c.id))" +
                                    "                   from conflicts c" +
                                    "                   left join events e on e.conflict_id = c.id" +
                                    "                   where e.date >= :from and e.date <= :to" +
                                    "                   and c.industry_id = :industryId" +
                                    "                   group by c.conflict_result_id) sub on sub.res_id = cr.id")
                            .setParameter("industryId", raw[1])
                            .setParameter("from", from)
                            .setParameter("to", to)
                            .getResultList())
                            .stream().collect(Collectors.toMap(
                                    raw1 -> mapKey(raw1[0]), // name of conflict result
                                    raw1 -> Optional.ofNullable(raw1[1])
                                            .map(count -> ((BigInteger) count).intValue())
                                            .orElse(0) // count of conflicts
                            ));
                    // to always return not specified count
                    countToResult.putIfAbsent(NOT_SPECIFIED, 0);
                    int totalConflictOfType = countToResult.values().stream().reduce(Integer::sum).orElse(0);
                    var countPercentToResult = new HashMap<String, Float>();
                    countToResult.forEach((key, value) -> countPercentToResult.put(key, value == 0 ? 0 : value.floatValue() * 100 / totalConflictOfType));
                    return countPercentToResult;
                }));
    }

    private String mapKey(Object key) {
        return (String) Optional.ofNullable(key).orElse(NOT_SPECIFIED);
    }
}
