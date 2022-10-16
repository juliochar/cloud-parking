package one.digitalinnovation.parking.service;

import one.digitalinnovation.parking.exception.ParkingNotFoundException;
import one.digitalinnovation.parking.model.Parking;
import one.digitalinnovation.parking.repository.ParkingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ParkingService {
    //hashmap enquanto nao trabalha direto com banco de dados

    /*
    private static Map<String, Parking> parkingMap = new HashMap<>();
    static {
        var id = getUUID();
        var id2 = getUUID();
        Parking parking = new Parking(id, "DMS-1111", "SC", "CELTA", "BRANCO");
        Parking parking1 = new Parking(id2, "WAS-1234", "SP", "VW GOL", "PRETO");
        parkingMap.put(id, parking);
        parkingMap.put(id2, parking1);
    }


     */

    private final ParkingRepository parkingRepository;

    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-","");
    }

    //métodos chamados estejam dentro de uma transação.
    //open connection
    //transaction.begin
    //transaction.commit
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Parking> findAll(){
        return parkingRepository.findAll();
        //return parkingMap.values().stream().collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public Parking findById(String id) {
        return parkingRepository.findById(id).orElseThrow(() ->
                new ParkingNotFoundException(id));
    }

    @Transactional
    public Parking create(Parking parkingCreate) {
        String uuid = getUUID();
        parkingCreate.setId(uuid);
        parkingCreate.setEntryDate(LocalDateTime.now());

        parkingRepository.save(parkingCreate);
        //parkingMap.put(uuid, parkingCreate);
        return parkingCreate;

    }

    @Transactional
    public void delete(String id) {
        findById(id);
        parkingRepository.deleteById(id);

        //parkingMap.remove(id);

    }

    @Transactional
    public Parking update(String id, Parking parkingUpdate) {
        Parking parking = findById(id);
        parking.setColor(parkingUpdate.getColor());
        parking.setState(parkingUpdate.getState());
        parking.setModel(parkingUpdate.getModel());
        parking.setLicense(parkingUpdate.getLicense());
        parkingRepository.save(parking);

        //parkingMap.replace(id, Parking);
        return parking;
    }

    @Transactional
    public Parking checkOut(String id) {
        //recuperar o estacionado
        //atualizar data de saida
        //calcular o valor

        Parking parking = findById(id);
        parking.setExitDate(LocalDateTime.now());

        parking.setBill(ParkingCheckOut.getBill(parking));
        parkingRepository.save(parking);


        return parking;

    }
}
