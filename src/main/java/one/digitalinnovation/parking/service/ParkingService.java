package one.digitalinnovation.parking.service;

import one.digitalinnovation.parking.exception.ParkingNotFoundException;
import one.digitalinnovation.parking.model.Parking;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ParkingService {
    //hashmap enquanto nao trabalha direto com banco de dados
    private static Map<String, Parking> parkingMap = new HashMap<>();
    static {
        var id = getUUID();
        var id2 = getUUID();
        Parking parking = new Parking(id, "DMS-1111", "SC", "CELTA", "BRANCO");
        Parking parking1 = new Parking(id2, "WAS-1234", "SP", "VW GOL", "PRETO");
        parkingMap.put(id, parking);
        parkingMap.put(id2, parking1);
    }

    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-","");


    }

    public List<Parking> findAll(){
        return parkingMap.values().stream().collect(Collectors.toList());

    }

    public Parking findById(String id) {
        Parking parking = parkingMap.get(id);
        if(parking == null){
            throw new ParkingNotFoundException(id);
        }
        return parking;
    }

    public Parking create(Parking parkingCreate) {
        String uuid = getUUID();
        parkingCreate.setId(uuid);
        parkingCreate.setEntryDate(LocalDateTime.now());
        parkingMap.put(uuid, parkingCreate);
        return parkingCreate;

    }

    public void delete(String id) {
        findById(id);
        parkingMap.remove(id);

    }

    public Parking update(String id, Parking parkingUpdate) {
        Parking Parking = findById(id);
        Parking.setColor(parkingUpdate.getColor());
        parkingMap.replace(id, Parking);
        return Parking;
    }

    public Parking exit(String id) {
        //recuperar o estacionado
        //atualizar data de saida
        //calcular o valor
        return null;
    }
}
