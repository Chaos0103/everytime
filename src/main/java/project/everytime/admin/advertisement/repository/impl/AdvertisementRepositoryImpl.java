package project.everytime.admin.advertisement.repository.impl;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import project.everytime.admin.advertisement.Advertisement;
import project.everytime.admin.advertisement.AdvertisementType;
import project.everytime.admin.advertisement.QAdvertisement;
import project.everytime.admin.advertisement.repository.AdvertisementRepositoryCustom;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static project.everytime.admin.advertisement.QAdvertisement.*;

public class AdvertisementRepositoryImpl implements AdvertisementRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public AdvertisementRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Advertisement> findAdvertisement(LocalDateTime currentTime) {

        List<Advertisement> result = new ArrayList<>();

        List<Advertisement> mainAdvertisement = queryFactory
                .selectFrom(advertisement)
                .where(
                        advertisement.expiredDate.goe(currentTime),
                        advertisement.type.eq(AdvertisementType.MAIN)
                )
                .fetch();

        int mainIndex = (int) (Math.random() * mainAdvertisement.size());
        result.add(mainAdvertisement.get(mainIndex));

        List<Advertisement> sideAdvertisement = queryFactory
                .selectFrom(advertisement)
                .where(
                        advertisement.expiredDate.goe(currentTime),
                        advertisement.type.eq(AdvertisementType.SIDE)
                )
                .fetch();

        List<Integer> indexList = new ArrayList<>();
        int count = 0;
        while (count < 3) {
            int sideIndex = (int) (Math.random() * sideAdvertisement.size());
            if (indexList.contains(sideIndex)) {
                continue;
            }
            result.add(sideAdvertisement.get(sideIndex));
            indexList.add(sideIndex);
            count++;
        }
        return result;
    }
}
