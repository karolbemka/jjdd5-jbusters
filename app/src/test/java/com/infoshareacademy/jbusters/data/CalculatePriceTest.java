package com.infoshareacademy.jbusters.data;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class CalculatePriceTest {

    private static final double TREND_PER_YEAR = 0.002;
    private static final double MAX_TREND_RATE_PER_DAY = 0.000274;
    private static final double MIN_TREND_RATE_PER_DAY = -0.000274;
    private CalculatePrice testObj = new CalculatePrice();

    // updatePrice

    @Test
    public void shouldReturnSamePrice_whenOverallTrendIs_0() {
        // given

        List<Transaction> transactions = new ArrayList<>();
        Transaction trans = new Transaction();
        trans.setTransactionDate(LocalDate.of(2018, 6, 20));
        trans.setPricePerM2(BigDecimal.valueOf(5500));

        transactions.add(trans);

        CalculatePrice test = Mockito.spy(new CalculatePrice());
        test.setFilteredList(transactions);
        test.setUserTransaction(trans);

        doReturn(transactions).when(test).getListToCalculateTrend(transactions);

        doReturn(BigDecimal.valueOf(0)).when(test).overallTrend(transactions);

        // when
        List<Transaction> result = test.updatePricesInList();

        //then
        assertThat(result.get(0).getPricePerM2()).isEqualTo(BigDecimal.valueOf(5500.00).setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    public void shouldReturnCorrectValue_whenTrendIsNot_0() {
        // given

        List<Transaction> transactions = new ArrayList<>();
        Transaction trans = new Transaction();
        trans.setTransactionDate(LocalDate.now().minusDays(365));
        trans.setPricePerM2(BigDecimal.valueOf(5500));

        transactions.add(trans);

        CalculatePrice test = Mockito.spy(new CalculatePrice());
        test.setFilteredList(transactions);
        test.setUserTransaction(trans);

        doReturn(transactions).when(test).getListToCalculateTrend(transactions);

        doReturn(BigDecimal.valueOf(TREND_PER_YEAR)).when(test).overallTrend(transactions);

        // when
        List<Transaction> result = test.updatePricesInList();

        //then
        assertThat(result.get(0).getPricePerM2()).isEqualTo(BigDecimal.valueOf(9515.00).setScale(2, RoundingMode.HALF_UP));
    }

    // getMaxPriceInList

    @Test
    public void maxPrice_shouldReturnMaxPriceInList() {

        //given

        List<Transaction> transactions = new ArrayList<>();

        Transaction lowerPriceT = new Transaction();
        Transaction midPriceT = new Transaction();
        Transaction higherPriceT = new Transaction();
        lowerPriceT.setPricePerM2(BigDecimal.valueOf(2000));
        midPriceT.setPricePerM2(BigDecimal.valueOf(4000));
        higherPriceT.setPricePerM2(BigDecimal.valueOf(6000));

        transactions.add(lowerPriceT);
        transactions.add(midPriceT);
        transactions.add(higherPriceT);

        // when

        BigDecimal result = testObj.getMaxPriceInList(transactions);

        // then

        assertThat(result).isEqualTo(BigDecimal.valueOf(6000.0));
    }

    @Test
    public void maxPrice_shouldReturnZeroWhenListIsEmpty() {

        //given

        List<Transaction> transactions = new ArrayList<>();

        // when

        BigDecimal result = testObj.getMaxPriceInList(transactions);

        // then

        assertThat(result).isEqualTo(BigDecimal.valueOf(0.0));
    }

    // getMinimumPriceInList

    @Test
    public void minPrice_shouldReturnMinPriceInList() {

        //given

        List<Transaction> transactions = new ArrayList<>();

        Transaction lowerPriceT = new Transaction();
        Transaction midPriceT = new Transaction();
        Transaction higherPriceT = new Transaction();
        lowerPriceT.setPricePerM2(BigDecimal.valueOf(2000));
        midPriceT.setPricePerM2(BigDecimal.valueOf(4000));
        higherPriceT.setPricePerM2(BigDecimal.valueOf(6000));

        transactions.add(lowerPriceT);
        transactions.add(midPriceT);
        transactions.add(higherPriceT);

        // when

        BigDecimal result = testObj.getMinimumPriceInList(transactions);

        // then

        assertThat(result).isEqualTo(BigDecimal.valueOf(2000.0));
    }

    @Test
    public void minPrice_shouldReturnZeroWhenListIsEmpty() {

        //given

        List<Transaction> transactions = new ArrayList<>();

        // when

        BigDecimal result = testObj.getMinimumPriceInList(transactions);

        // then

        assertThat(result).isEqualTo(BigDecimal.valueOf(0.0));
    }

    // getAvaragePriceInList

    @Test
    public void averagePrice_shouldReturnAveragePriceInList() {

        //given

        List<Transaction> transactions = new ArrayList<>();

        Transaction lowerPriceT = new Transaction();
        Transaction midPriceT = new Transaction();
        Transaction higherPriceT = new Transaction();
        lowerPriceT.setPricePerM2(BigDecimal.valueOf(2000));
        midPriceT.setPricePerM2(BigDecimal.valueOf(4000));
        higherPriceT.setPricePerM2(BigDecimal.valueOf(6000));

        transactions.add(lowerPriceT);
        transactions.add(midPriceT);
        transactions.add(higherPriceT);

        // when

        BigDecimal result = testObj.getAvaragePriceInList(transactions);

        // then

        assertThat(result).isEqualTo(BigDecimal.valueOf(4000.0));
    }

    @Test
    public void averagePrice_shouldReturnZeroWhenListIsEmpty() {

        //given

        List<Transaction> transactions = new ArrayList<>();

        // when

        BigDecimal result = testObj.getAvaragePriceInList(transactions);

        // then

        assertThat(result).isEqualTo(BigDecimal.valueOf(0.0));
    }

    // isBestLevel

    @Test
    public void isBestLevel_shouldReturnTrueWhen_levelIs_2() {

        // given

        Transaction transToCheck = new Transaction();
        transToCheck.setLevel(2);

        // when

        boolean result = testObj.isBestLevel(transToCheck);

        // then

        assertThat(result).isTrue();
    }

    @Test
    public void isBestLevel_shouldReturnFalseWhen_levelIs_1() {

        // given

        Transaction transToCheck = new Transaction();
        transToCheck.setLevel(1);

        // when

        boolean result = testObj.isBestLevel(transToCheck);

        // then

        assertThat(result).isFalse();
    }

    // isBestFlatArea

    @Test
    public void isBestFlatArea_shouldReturnFalseWhen_FlatAreaIsAboveAverage() {

        //given

        List<Transaction> transactions = new ArrayList<>();

        Transaction lowerPriceT = new Transaction();
        Transaction midPriceT = new Transaction();
        Transaction higherPriceT = new Transaction();
        Transaction transToCheck = new Transaction();
        lowerPriceT.setFlatArea(BigDecimal.valueOf(30));
        midPriceT.setFlatArea(BigDecimal.valueOf(50));
        higherPriceT.setFlatArea(BigDecimal.valueOf(70));
        transToCheck.setFlatArea(BigDecimal.valueOf(51));

        transactions.add(lowerPriceT);
        transactions.add(midPriceT);
        transactions.add(higherPriceT);

        // when

        boolean result = testObj.isBestFlatArea(transactions, transToCheck);

        // then

        assertThat(result).isFalse();
    }

    @Test
    public void isBestFlatArea_shouldReturnTrueWhen_FlatAreaIsBelowAverage() {

        //given

        List<Transaction> transactions = new ArrayList<>();

        Transaction lowerPriceT = new Transaction();
        Transaction midPriceT = new Transaction();
        Transaction higherPriceT = new Transaction();
        Transaction transToCheck = new Transaction();
        lowerPriceT.setFlatArea(BigDecimal.valueOf(30));
        midPriceT.setFlatArea(BigDecimal.valueOf(50));
        higherPriceT.setFlatArea(BigDecimal.valueOf(70));
        transToCheck.setFlatArea(BigDecimal.valueOf(49));

        transactions.add(lowerPriceT);
        transactions.add(midPriceT);
        transactions.add(higherPriceT);

        // when

        boolean result = testObj.isBestFlatArea(transactions, transToCheck);

        // then

        assertThat(result).isTrue();
    }

    // isBestStandardLevel

    @Test
    public void isBestStandardLevel_shouldReturnTrueWhen_standardLevelIsBetterThanBestInList() {

        //given

        List<Transaction> transactions = new ArrayList<>();

        Transaction niskiStandard = new Transaction();
        Transaction dobryStandard = new Transaction();
        Transaction transToCheck = new Transaction();
        niskiStandard.setStandardLevel(StandardLevel.NISKI.getName());
        dobryStandard.setStandardLevel(StandardLevel.DOBRY.getName());
        transToCheck.setStandardLevel(StandardLevel.BARDZO_DOBRY.getName());

        transactions.add(niskiStandard);
        transactions.add(dobryStandard);

        // when

        boolean result = testObj.isBestStandardLevel(transactions, transToCheck);

        // then

        assertThat(result).isTrue();
    }

    @Test
    public void isBestStandardLevel_shouldReturnTrueWhen_standardLevelIsSameAsBestInList() {

        //given

        List<Transaction> transactions = new ArrayList<>();

        Transaction niskiStandard = new Transaction();
        Transaction dobryStandard = new Transaction();
        Transaction transToCheck = new Transaction();
        niskiStandard.setStandardLevel(StandardLevel.NISKI.getName());
        dobryStandard.setStandardLevel(StandardLevel.DOBRY.getName());
        transToCheck.setStandardLevel(StandardLevel.DOBRY.getName());

        transactions.add(niskiStandard);
        transactions.add(dobryStandard);

        // when

        boolean result = testObj.isBestStandardLevel(transactions, transToCheck);

        // then

        assertThat(result).isTrue();
    }

    @Test
    public void isBestStandardLevel_shouldReturnFalseWhen_standardLevelIsWorseThanBestInList() {

        //given

        List<Transaction> transactions = new ArrayList<>();

        Transaction niskiStandard = new Transaction();
        Transaction dobryStandard = new Transaction();
        Transaction transToCheck = new Transaction();
        niskiStandard.setStandardLevel(StandardLevel.NISKI.getName());
        dobryStandard.setStandardLevel(StandardLevel.DOBRY.getName());
        transToCheck.setStandardLevel(StandardLevel.PRZECIETNY.getName());

        transactions.add(niskiStandard);
        transactions.add(dobryStandard);

        // when

        boolean result = testObj.isBestStandardLevel(transactions, transToCheck);

        // then

        assertThat(result).isFalse();
    }

    // isBestParkingPlace

    @Test
    public void isBestParkingPlace_shouldReturnTrueWhen_parkingPlaceIsBetterThanBestInList() {

        //given

        List<Transaction> transactions = new ArrayList<>();

        Transaction firstOption = new Transaction();
        Transaction secondOption = new Transaction();
        Transaction transToCheck = new Transaction();
        firstOption.setParkingSpot(ParkingPlace.BRAK_MP.getName());
        secondOption.setParkingSpot(ParkingPlace.MIEJSCE_HALA.getName());
        transToCheck.setParkingSpot(ParkingPlace.GARAZ.getName());

        transactions.add(firstOption);
        transactions.add(secondOption);

        // when

        boolean result = testObj.isBestParkingPlace(transactions, transToCheck);

        // then

        assertThat(result).isTrue();
    }

    @Test
    public void isBestParkingPlace_shouldReturnTrueWhen_parkingPlaceIsSameAsBestInList() {

        //given

        List<Transaction> transactions = new ArrayList<>();

        Transaction firstOption = new Transaction();
        Transaction secondOption = new Transaction();
        Transaction transToCheck = new Transaction();
        firstOption.setParkingSpot(ParkingPlace.BRAK_MP.getName());
        secondOption.setParkingSpot(ParkingPlace.MIEJSCE_HALA.getName());
        transToCheck.setParkingSpot(ParkingPlace.MIEJSCE_HALA.getName());

        transactions.add(firstOption);
        transactions.add(secondOption);

        // when

        boolean result = testObj.isBestParkingPlace(transactions, transToCheck);

        // then

        assertThat(result).isTrue();
    }

    @Test
    public void isBestParkingPlace_shouldReturnFalseWhen_parkingPlaceIsWorseThanBestInList() {

        //given

        List<Transaction> transactions = new ArrayList<>();

        Transaction firstOption = new Transaction();
        Transaction secondOption = new Transaction();
        Transaction transToCheck = new Transaction();
        firstOption.setParkingSpot(ParkingPlace.BRAK_MP.getName());
        secondOption.setParkingSpot(ParkingPlace.MIEJSCE_HALA.getName());
        transToCheck.setParkingSpot(ParkingPlace.MIEJSCE_NAZIEMNE.getName());

        transactions.add(firstOption);
        transactions.add(secondOption);

        // when

        boolean result = testObj.isBestParkingPlace(transactions, transToCheck);

        // then

        assertThat(result).isFalse();
    }

    // getTabs

    @Test
    public void getTabsTest() {

        // given

        int i0 = 0;
        int i1 = 1;
        int i3 = 3;

        // when

        String result1 = testObj.getTabs(i0);
        String result2 = testObj.getTabs(i1);
        String result3 = testObj.getTabs(i3);

        // then
        assertThat(result1).isEqualTo("");
        assertThat(result2).isEqualTo("\t");
        assertThat(result3).isEqualTo("\t\t\t");
    }

    // trendPerDay

    @Test
    public void trendPerDay() {

        // given

        LocalDate date = LocalDate.of(2018, 6, 1);

        List<Transaction> transactions = new ArrayList<>();
        Transaction transOne = new Transaction();
        transOne.setTransactionDate(date);
        transOne.setPricePerM2(BigDecimal.valueOf(5000));

        Transaction transTwo = new Transaction();
        transTwo.setTransactionDate(date.plusDays(365));
        transTwo.setPricePerM2(BigDecimal.valueOf(5250));

        Transaction transThree = new Transaction();
        transThree.setTransactionDate(date.plusDays(365));
        transThree.setPricePerM2(BigDecimal.valueOf(6000));

        Transaction transFour = new Transaction();
        transFour.setTransactionDate(date.plusDays(365));
        transFour.setPricePerM2(BigDecimal.valueOf(4000));


        transactions.add(transOne);
        transactions.add(transTwo);
        transactions.add(transThree);
        transactions.add(transFour);

        // when

        BigDecimal resultOne = testObj.trendPerDay(transactions, 0, 1);
        BigDecimal resultTwo = testObj.trendPerDay(transactions, 0, 2);
        BigDecimal resultThree = testObj.trendPerDay(transactions, 0, 3);
        BigDecimal resultFour = testObj.trendPerDay(transactions, 0, 0);

        // then

        assertThat(resultOne).isEqualTo(BigDecimal.valueOf(0.000136986).setScale(6, RoundingMode.HALF_UP));
        assertThat(resultTwo).isEqualTo(BigDecimal.valueOf(MAX_TREND_RATE_PER_DAY));
        assertThat(resultThree).isEqualTo(BigDecimal.valueOf(MIN_TREND_RATE_PER_DAY));
        assertThat(resultFour).isEqualTo(BigDecimal.valueOf(0.000000).setScale(6, RoundingMode.HALF_UP));
    }

    // mapOfParkingSpots

    @Test
    public void mapOfParkingSpots() {

        // given

        List<Transaction> transactions = new ArrayList<>();

        Transaction transOne = new Transaction();
        transOne.setParkingSpot(ParkingPlace.GARAZ.getName());
        Transaction transTwo = new Transaction();
        transTwo.setParkingSpot(ParkingPlace.BRAK_MP.getName());
        Transaction transThree = new Transaction();
        transThree.setParkingSpot(ParkingPlace.MIEJSCE_HALA.getName());
        Transaction transFour = new Transaction();
        transFour.setParkingSpot(ParkingPlace.MIEJSCE_HALA.getName());

        transactions.add(transOne);
        transactions.add(transTwo);
        transactions.add(transThree);
        transactions.add(transFour);

        // when
        Map<String, Long> result = testObj.mapOfParkingSpots(transactions);

        // then

        assertThat(result).hasSize(3);
        assertThat(result).contains(entry("GARAŻ JEDNOSTANOWISKOWY", 1L),
                entry("BRAK MP", 1L), entry("MIEJSCE POSTOJOWE W HALI GARAŻOWEJ", 2L));
    }

    // mapOfStandardLevel

    @Test
    public void mapOfStandardLevel() {

        // given
        List<Transaction> transactions = new ArrayList<>();

        Transaction transOne = new Transaction();
        transOne.setStandardLevel(StandardLevel.DOBRY.getName());
        Transaction transTwo = new Transaction();
        transTwo.setStandardLevel(StandardLevel.DOBRY.getName());
        Transaction transThree = new Transaction();
        transThree.setStandardLevel(StandardLevel.DOBRY.getName());
        Transaction transFour = new Transaction();
        transFour.setStandardLevel(StandardLevel.PRZECIETNY.getName());

        transactions.add(transOne);
        transactions.add(transTwo);
        transactions.add(transThree);
        transactions.add(transFour);

        // when

        Map<String, Long> result = testObj.mapOfStandardLevel(transactions);

        // then
        assertThat(result).hasSize(2);
        assertThat(result).contains(entry("DOBRY", 3L), entry("PRZECIĘTNY", 1L));
    }
}