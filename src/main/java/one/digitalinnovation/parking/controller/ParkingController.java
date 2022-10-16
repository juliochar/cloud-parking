package one.digitalinnovation.parking.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import one.digitalinnovation.parking.controller.dto.ParkingCreateDTO;
import one.digitalinnovation.parking.controller.dto.ParkingDTO;
import one.digitalinnovation.parking.controller.mapper.ParkingMapper;
import one.digitalinnovation.parking.model.Parking;
import one.digitalinnovation.parking.service.ParkingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking")
@Api(tags= "Parking Controller")
public class ParkingController {

    //Spring recomenda usar injeção de dependencia por construtor
    private final ParkingService parkingService;
    private final ParkingMapper parkingMapper;


    public ParkingController(ParkingService parkingService, ParkingMapper parkingMapper) {
        this.parkingService = parkingService;
        this.parkingMapper = parkingMapper;
    }

    @GetMapping
    @ApiOperation("Encontrar todos os 'Parkings'")
    public ResponseEntity<List<ParkingDTO>> findAll(){

        List<Parking> parkingList = parkingService.findAll();
        List<ParkingDTO> result = parkingMapper.toParkingDTOList(parkingList);
        return ResponseEntity.ok(result);

    }

    @GetMapping("/{id}")
    @ApiOperation("Buscar por ID")
    public ResponseEntity<ParkingDTO> findById(@PathVariable String id){

        Parking parking = parkingService.findById(id);

        ParkingDTO result = parkingMapper.toparkingDTO(parking);
        return ResponseEntity.ok(result);

    }

    @DeleteMapping("/{id}")
    @ApiOperation("Deletar por um ID especifico")
    public ResponseEntity delete(@PathVariable String id){

        parkingService.delete(id);
        return ResponseEntity.noContent().build();

    }

    @PostMapping
    @ApiOperation("Criar um novo 'Parking'")
    public ResponseEntity<ParkingDTO> create(@RequestBody ParkingCreateDTO dto){
        var parkingCreate = parkingMapper.toParkingCreate(dto);
        Parking parking = parkingService.create(parkingCreate);
        var result = parkingMapper.toparkingDTO(parking);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);

    }

    @PutMapping("{/id}")
    @ApiOperation("Update - Atualizar um 'parking'")
    public ResponseEntity<ParkingDTO> update(@PathVariable String id,@RequestBody ParkingCreateDTO dto){
        var parkingUpdate = parkingMapper.toParkingCreate(dto);
        Parking parking = parkingService.update(id, parkingUpdate);
        return ResponseEntity.ok(parkingMapper.toparkingDTO(parking));
    }

    @PostMapping("{/id}")
    @ApiOperation("Post - Saida do Estacionamento")
    public ResponseEntity<ParkingDTO> exit(@PathVariable String id){
        Parking parking = parkingService.exit(id);
        return ResponseEntity.ok(parkingMapper.toparkingDTO(parking));
    }


}
