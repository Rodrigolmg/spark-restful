package br.com.spark.service;


import br.com.spark.model.Car;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

public class ServiceAPI {

    final static Logger LOGGER = LogManager.getLogger(ServiceAPI.class);
    final static String CONTENT_TYPE = "application/json";

    public static void main(String[] args) {
        cars();
    }

    private static void cars(){
        List<Car> carList = new ArrayList<>();

        port(8090);

        path("/api", () -> {
            before("/*", (request, response) -> LOGGER.info("Received API call"));

            path("/car", () -> {

                get("/g", (request, response) -> {
                    response.type(CONTENT_TYPE);
                    LOGGER.info("Acesso ao GET");
                    response.status(200);
                    return new Gson().toJson(carList);
                });

                post("/pst", (request, response) -> {
                    response.type(CONTENT_TYPE);
                    Car car = new Gson().fromJson(request.body(), Car.class);
                    carList.add(car);
                    LOGGER.info("Acesso ao POST");
                    response.status(201);
                    return new Gson().toJson(car);
                });

                put("/pt", (request, response) -> {
                    response.type(CONTENT_TYPE);
                    Car car = new Gson().fromJson(request.body(), Car.class);
                    carList.removeIf(c -> c.getId().equals(car.getId()));
                    carList.add(car);

                    LOGGER.info("Acesso ao PUT.");
                    response.status(201);
                    return new Gson().toJson(car);
                });

                delete("/del/:id", (request, response) -> {

                    Car car = new Car();
                    car.setId(Long.parseLong(request.params(":id")));
                    carList.removeIf(c -> c.getId().equals(car.getId()));
                    LOGGER.info("Acesso ao DELETE");
                    response.status(204);
                    String msg = "Carro deletado com sucesso!";
                    return msg;
                });



            });
        });
    }
}
