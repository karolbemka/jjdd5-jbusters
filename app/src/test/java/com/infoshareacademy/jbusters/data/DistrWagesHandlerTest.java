package com.infoshareacademy.jbusters.data;

import com.infoshareacademy.jbusters.dao.DistrictWageDao;
import com.infoshareacademy.jbusters.model.DistrictWage;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class DistrWagesHandlerTest {


    @Test
    void districtWageComparator() {
        // given
        DistrictWageDao districtWageDao = mock(DistrictWageDao.class);
        DistrWagesHandler testObj = new DistrWagesHandler(districtWageDao) ;
        Transaction transaction = new Transaction();
        String city = "Gdynia";
        String district = "Chylonia";
        transaction.setCity(city);
        transaction.setDistrict(district);

        int wageOne = 1;
        DistrictWage districtWageOne = new DistrictWage();
        districtWageOne.setWage(wageOne);

        doReturn(districtWageOne).when(districtWageDao).findByName(anyString(), anyString());

        // when

        boolean result = testObj.districtWageComparator(transaction, transaction);

        // then

        assertThat(result).isTrue();
    }
}