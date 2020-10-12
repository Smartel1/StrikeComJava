package ru.smartel.strike.repository.conflict;

import org.springframework.transaction.annotation.Transactional;
import ru.smartel.strike.dto.response.conflict.report.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Transactional(rollbackFor = Exception.class)
@SuppressWarnings("unchecked")
public class ReportsConflictRepositoryImpl implements ReportsConflictRepository {
    public static final String NOT_SPECIFIED = "Не указано";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long getOldConflictsCount(LocalDate from, LocalDate to, List<Long> countriesIds) {
        return ((BigInteger) entityManager.createNativeQuery(
                "select count(distinct(c.id))" +
                        " from conflicts c" +
                        " left join events e on e.conflict_id = c.id" +
                        " left join localities l on e.locality_id = l.id" +
                        " left join regions r on l.region_id = r.id" +
                        " where e.date >= :from and e.date <= :to" +
                        " and r.country_id in :countriesIds" +
                        " and c.date_from < :from")
                .setParameter("from", from)
                .setParameter("to", to)
                .setParameter("countriesIds", countriesIds)
                .getSingleResult()).longValue();
    }

    @Override
    public List<CountByCountry> getCountByCountries(LocalDate from, LocalDate to, List<Long> countriesIds) {
        // conflict's country is the country of the first conflict's event (first by date)
        List<Object[]> resultList = entityManager.createNativeQuery(
                "with sub as (" +
                        "    select e.conflict_id," +
                        "           e.locality_id," +
                        "           ROW_NUMBER() OVER (PARTITION BY e.conflict_id ORDER BY e.date) AS rk" +
                        "    from events e where e.date >= :from and e.date <= :to)" +
                        " select r.country_id, count(conflict_id)" +
                        " from sub" +
                        "         left join localities l on l.id = sub.locality_id" +
                        "         left join regions r on r.id = l.region_id" +
                        " where rk = 1" +
                        " and r.country_id in :countriesIds" +
                        " group by r.country_id")
                .setParameter("from", from)
                .setParameter("to", to)
                .setParameter("countriesIds", countriesIds)
                .getResultList();

        return resultList.stream()
                .map(r -> new CountByCountry(mapNullableId(r[0]), ((BigInteger) r[1]).longValue()))
                .collect(toList());
    }

    @Override
    public Map<String, Integer> getCountByDistricts(LocalDate from, LocalDate to, List<Long> countriesIds) {
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
                        " and r.country_id in :countriesIds" +
                        " group by d.name")
                .setParameter("from", from)
                .setParameter("to", to)
                .setParameter("countriesIds", countriesIds)
                .getResultList();

        return resultList.stream()
                .collect(Collectors.toMap(r -> mapKey(r[0]), r -> ((BigInteger) r[1]).intValue()));
    }

    @Override
    public Map<String, Float> getSpecificCountByDistricts(LocalDate from, LocalDate to, List<Long> countriesIds) {
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
                        " and r.country_id in :countriesIds" +
                        " group by d.name" +
                        " having d.name is not null")
                .setParameter("from", from)
                .setParameter("to", to)
                .setParameter("countriesIds", countriesIds)
                .getResultList();

        return resultList.stream()
                .collect(Collectors.toMap(r -> mapKey(r[0]), r -> ((Double) r[1]).floatValue()));
    }

    @Override
    public List<CountByIndustry> getCountByIndustries(LocalDate from, LocalDate to, List<Long> countriesIds) {
        List<Object[]> resultList = entityManager.createNativeQuery(
                "select c.industry_id, count(distinct(c.id))" +
                        " from conflicts c" +
                        " left join events e on e.conflict_id = c.id" +
                        " left join localities l on e.locality_id = l.id" +
                        " left join regions r on l.region_id = r.id" +
                        " where e.date >= :from and e.date <= :to" +
                        " and r.country_id in :countriesIds" +
                        " group by c.industry_id")
                .setParameter("from", from)
                .setParameter("to", to)
                .setParameter("countriesIds", countriesIds)
                .getResultList();

        return resultList.stream()
                .map(r -> new CountByIndustry(mapNullableId(r[0]), ((BigInteger) r[1]).longValue()))
                .collect(toList());
    }

    @Override
    public List<CountByReason> getCountByReasons(LocalDate from, LocalDate to, List<Long> countriesIds) {
        List<Object[]> resultList = entityManager.createNativeQuery(
                "select c.conflict_reason_id, count(distinct(c.id))" +
                        " from conflicts c" +
                        " left join events e on e.conflict_id = c.id" +
                        " left join localities l on e.locality_id = l.id" +
                        " left join regions r on l.region_id = r.id" +
                        " where e.date >= :from and e.date <= :to" +
                        " and r.country_id in :countriesIds" +
                        " group by c.conflict_reason_id")
                .setParameter("from", from)
                .setParameter("to", to)
                .setParameter("countriesIds", countriesIds)
                .getResultList();

        return resultList.stream()
                .map(r -> new CountByReason(mapNullableId(r[0]), ((BigInteger) r[1]).longValue()))
                .collect(toList());
    }

    @Override
    public List<CountByResult> getCountByResults(LocalDate from, LocalDate to, List<Long> countriesIds) {
        List<Object[]> resultList = entityManager.createNativeQuery(
                "select c.conflict_result_id, count(distinct(c.id))" +
                        " from conflicts c" +
                        " left join events e on e.conflict_id = c.id" +
                        " left join localities l on e.locality_id = l.id" +
                        " left join regions r on l.region_id = r.id" +
                        " where e.date >= :from and e.date <= :to" +
                        " and r.country_id in :countriesIds" +
                        " group by c.conflict_result_id")
                .setParameter("from", from)
                .setParameter("to", to)
                .setParameter("countriesIds", countriesIds)
                .getResultList();

        return resultList.stream()
                .map(r -> new CountByResult(mapNullableId(r[0]), ((BigInteger) r[1]).longValue()))
                .collect(toList());
    }

    @Override
    public List<CountByType> getCountByTypes(LocalDate from, LocalDate to, List<Long> countriesIds) {
        List<Object[]> resultList = entityManager.createNativeQuery(
                "select c.main_type_id, count(distinct(c.id))" +
                        " from conflicts c" +
                        " left join events e on e.conflict_id = c.id" +
                        " left join localities l on e.locality_id = l.id" +
                        " left join regions r on l.region_id = r.id" +
                        " where e.date >= :from and e.date <= :to" +
                        " and r.country_id in :countriesIds" +
                        " group by c.main_type_id")
                .setParameter("from", from)
                .setParameter("to", to)
                .setParameter("countriesIds", countriesIds)
                .getResultList();

        return resultList.stream()
                .map(r -> new CountByType(mapNullableId(r[0]), ((BigInteger) r[1]).longValue()))
                .collect(toList());
    }

    @Override
    public List<CountByResultsByType> getCountByResultsByTypes(LocalDate from, LocalDate to, List<Long> countriesIds) {
        var types = (List<Integer>) entityManager.createNativeQuery(
                "select id from event_types")
                .getResultList();
        return types.stream()
                .map(mainTypeId -> new CountByResultsByType(mainTypeId.longValue(), getCountByResult(from, to, countriesIds, mainTypeId.longValue())))
                .collect(toList());
    }

    private List<CountByResult> getCountByResult(LocalDate from, LocalDate to, List<Long> countriesIds, long mainTypeId) {
        var countToResult = ((List<Object[]>) entityManager.createNativeQuery(
                "select cr.id, sub.count" +
                        " from conflict_results cr" +
                        "   full outer join (select c.conflict_result_id res_id, count(distinct(c.id))" +
                        "                   from conflicts c" +
                        "                   left join events e on e.conflict_id = c.id" +
                        "                   left join localities l on e.locality_id = l.id" +
                        "                   left join regions r on l.region_id = r.id" +
                        "                   where e.date >= :from and e.date <= :to" +
                        "                   and r.country_id in :countriesIds" +
                        "                   and c.main_type_id = :mainTypeId" +
                        "                   group by c.conflict_result_id) sub on sub.res_id = cr.id")
                .setParameter("mainTypeId", mainTypeId)
                .setParameter("from", from)
                .setParameter("to", to)
                .setParameter("countriesIds", countriesIds)
                .getResultList())
                .stream()
                .map(raw -> new CountByResult(
                        Optional.ofNullable((Integer) raw[0]).map(Integer::longValue).orElse(null), // may be null
                        Optional.ofNullable(raw[1])
                                .map(count -> ((BigInteger) count).intValue())
                                .orElse(0))) // count of conflicts)
                .collect(toList());
        // to always return not specified count
        if (countToResult.stream().noneMatch(ctr -> ctr.getResultId() == null)) {
            countToResult.add(new CountByResult(null, 0));
        }

        return countToResult;
    }

    @Override
    public List<CountByResultsByIndustry> getCountByResultsByIndustries(LocalDate from, LocalDate to, List<Long> countriesIds) {
        var industryIds = (List<Integer>) entityManager.createNativeQuery(
                "select id from industries")
                .getResultList();
        return industryIds.stream()
                .map(industryId -> new CountByResultsByIndustry(industryId.longValue(), getCountByResultByIndustry(from, to, countriesIds, industryId.longValue())))
                .collect(toList());
    }

    private List<CountByResult> getCountByResultByIndustry(LocalDate from, LocalDate to, List<Long> countriesIds, long industryId) {
        var countToResult = ((List<Object[]>) entityManager.createNativeQuery(
                "select cr.id, sub.count" +
                        " from conflict_results cr" +
                        "   full outer join (select c.conflict_result_id res_id, count(distinct(c.id))" +
                        "                   from conflicts c" +
                        "                   left join events e on e.conflict_id = c.id" +
                        "                   left join localities l on e.locality_id = l.id" +
                        "                   left join regions r on l.region_id = r.id" +
                        "                   where e.date >= :from and e.date <= :to" +
                        "                   and r.country_id in :countriesIds" +
                        "                   and c.industry_id = :industryId" +
                        "                   group by c.conflict_result_id) sub on sub.res_id = cr.id")
                .setParameter("industryId", industryId)
                .setParameter("from", from)
                .setParameter("to", to)
                .setParameter("countriesIds", countriesIds)
                .getResultList())
                .stream()
                .map(raw -> new CountByResult(
                        Optional.ofNullable((Integer) raw[0]).map(Integer::longValue).orElse(null), // may be null
                        Optional.ofNullable(raw[1])
                                .map(count -> ((BigInteger) count).intValue())
                                .orElse(0))) // count of conflicts)
                .collect(toList());
        // to always return not specified count
        if (countToResult.stream().noneMatch(ctr -> ctr.getResultId() == null)) {
            countToResult.add(new CountByResult(null, 0));
        }
        return countToResult;
    }

    @Override
    public List<CountByTypeByIndustry> getCountPercentByTypesByIndustries(LocalDate from, LocalDate to, List<Long> countriesIds) {
        var industryIds = (List<Integer>) entityManager.createNativeQuery(
                "select id from industries")
                .getResultList();
        return industryIds.stream()
                .map(industryId -> new CountByTypeByIndustry(industryId.longValue(), getCountByTypeByIndustry(from, to, countriesIds, industryId.longValue())))
                .collect(toList());
    }

    private List<CountByType> getCountByTypeByIndustry(LocalDate from, LocalDate to, List<Long> countriesIds, long industryId) {
        var countToType = ((List<Object[]>) entityManager.createNativeQuery(
                "select et.id, sub.count" +
                        " from event_types et" +
                        "   full outer join (select c.main_type_id mtype_id, count(distinct(c.id))" +
                        "                   from conflicts c" +
                        "                   left join events e on e.conflict_id = c.id" +
                        "                   left join localities l on e.locality_id = l.id" +
                        "                   left join regions r on l.region_id = r.id" +
                        "                   where e.date >= :from and e.date <= :to" +
                        "                   and r.country_id in :countriesIds" +
                        "                   and c.industry_id = :industryId" +
                        "                   group by c.main_type_id) sub on sub.mtype_id = et.id")
                .setParameter("industryId", industryId)
                .setParameter("from", from)
                .setParameter("to", to)
                .setParameter("countriesIds", countriesIds)
                .getResultList())
                .stream()
                .map(raw -> new CountByType(
                        Optional.ofNullable((Integer) raw[0]).map(Integer::longValue).orElse(null), // may be null
                        Optional.ofNullable(raw[1])
                                .map(count -> ((BigInteger) count).intValue())
                                .orElse(0))) // count of conflicts)
                .collect(toList());
        // to always return not specified count
        if (countToType.stream().noneMatch(ctr -> ctr.getTypeId() == null)) {
            countToType.add(new CountByType(null, 0));
        }
        return countToType;
    }

    private String mapKey(Object key) {
        return (String) Optional.ofNullable(key).orElse(NOT_SPECIFIED);
    }

    private Long mapNullableId(Object key) {
        return Optional.ofNullable(key)
                .map(k -> (Integer) k)
                .map(Integer::longValue)
                .orElse(null);
    }
}
