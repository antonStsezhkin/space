package com.space.controller;

import com.space.model.Ship;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class ShipUtils {
    public static final double MIN_SPEED = 0.01;
    public static final double MAX_SPEED = 0.99;
    public static final int MIN_CREW = 1;
    public static final int MAX_CREW = 9999;
    public static final long MIN_DATE = 26192235600000L;
    public static final long MAX_DATE = 33134734799999L;
    public static final int NAME_MAX_LEN = 50;
    public static final int PLANET_MAX_LEN = 50;

    /**
     * Валидирует корабль который надо сохранить.
     * Валидным считается корабль у которого:
     * -указаны все параметры из DataParams(кроме isUsed); isUsed по умолчанию false;
     * -длина значения параметра “name”или “planet” не пустая строка и не превышает размера соответствующего поля в БД(50 символов);
     * -скорость от 0.01 до 0.99;
     * -численность команды от 1 до 9999;
     * -год выпуска от 2800 до 3019 включительно;
     * Округляет дробные числа до сотых
     * Вычисляет и присваивает рейтинг корабля
     * @param ship
     * @return true - если корабль валиден, false - если нет
     */
    public static boolean prepareToSave(Ship ship){
        if(!isValid(ship)) return false;
        ship.setSpeed(round(ship.getSpeed(),2));
        if(ship.isUsed() == null) ship.setUsed(false);
        assignRating(ship);
        return true;
    }

    private static void assignRating(Ship ship){
        double k = ship.isUsed()? 0.5 : 1;
        final int y0 = 3019;
        Date prodDate = ship.getProdDate();
        int y1 = Integer.parseInt(new SimpleDateFormat("yyyy").format(prodDate));
        double rating = (80 * ship.getSpeed() * k)/(y0-y1+1);
        rating = round(rating, 2);
        ship.setRating(rating);
    }

    private static boolean isValid(Ship ship){
        try {
            if(ship.getCrewSize() < MIN_CREW || ship.getCrewSize() > MAX_CREW) return false;
            if(ship.getSpeed() < MIN_SPEED || ship.getSpeed() > MAX_SPEED) return false;
            if(ship.getProdDate().getTime() < MIN_DATE || ship.getProdDate().getTime() > MAX_DATE) return false;
            if(ship.getName().length() == 0 || ship.getName().length() > NAME_MAX_LEN) return false;
            if(ship.getPlanet().length() == 0 || ship.getPlanet().length() > PLANET_MAX_LEN) return false;
            if(ship.getShipType() == null) return false;
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public static boolean prepareToUpdate(Ship shipToEdit, Ship ship) {
        if(isValidForUpdate(ship)){
            updateValues(shipToEdit, ship);
            return true;
        }
        return false;
    }

    private static void updateValues(Ship shipToEdit, Ship ship) {
        boolean needRecalculateRating = false;
        if(ship.getCrewSize() != null){
            needRecalculateRating = true;
            shipToEdit.setCrewSize(ship.getCrewSize());
        }
        if(ship.getSpeed() != null){
            needRecalculateRating = true;
            shipToEdit.setSpeed(ship.getSpeed());
        }
        if(ship.getProdDate() != null){
            needRecalculateRating = true;
            shipToEdit.setProdDate(ship.getProdDate());
        }
        if(ship.isUsed() != null){
            needRecalculateRating = true;
            shipToEdit.setUsed(ship.isUsed());
        }
        if(ship.getName() != null){
            needRecalculateRating = true;
            shipToEdit.setName(ship.getName());
        }
        if(ship.getPlanet() != null){
            needRecalculateRating = true;
            shipToEdit.setPlanet(ship.getPlanet());
        }
        if(ship.getShipType() != null){
            needRecalculateRating = true;
            shipToEdit.setShipType(ship.getShipType());
        }
        if(needRecalculateRating){ assignRating(shipToEdit);}
    }

    private static boolean isValidForUpdate(Ship ship) {
        if(ship == null) return false;
        if(ship.getCrewSize() != null && (ship.getCrewSize() < MIN_CREW || ship.getCrewSize() > MAX_CREW)) return false;
        if(ship.getSpeed() != null && (ship.getSpeed() < MIN_SPEED || ship.getSpeed() > MAX_SPEED)) return false;
        if(ship.getProdDate() != null && (ship.getProdDate().getTime() < MIN_DATE || ship.getProdDate().getTime() > MAX_DATE)) return false;
        if(ship.getName() != null && (ship.getName().length() == 0 || ship.getName().length() > NAME_MAX_LEN)) return false;
        if(ship.getPlanet() != null && (ship.getPlanet().length() == 0 || ship.getPlanet().length() > PLANET_MAX_LEN)) return false;
        return true;
    }

    private static double round(double number, int places){
       return new BigDecimal(Double.toString(number)).setScale(places, RoundingMode.HALF_EVEN).doubleValue();
    }
}
