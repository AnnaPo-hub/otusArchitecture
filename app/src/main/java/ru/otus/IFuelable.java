package ru.otus;


public interface IFuelable {
    double getFuel();

    double getFuelBurnRate();

    void setFuel(double newFuelQuantity);
}
