package com.yil.adress.controller;

import com.yil.adress.base.Mapper;
import com.yil.adress.base.ApiConstant;
import com.yil.adress.base.PageDto;
import com.yil.adress.base.SortOrderConverter;
import com.yil.adress.dto.CityDto;
import com.yil.adress.dto.CityResponse;
import com.yil.adress.dto.CountryResponse;
import com.yil.adress.dto.CreateCityDto;
import com.yil.adress.exception.CountryNotFoundException;
import com.yil.adress.model.City;
import com.yil.adress.model.Country;
import com.yil.adress.service.CityService;
import com.yil.adress.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/adr/v1/cities")
public class CityController {
    private final CityService cityService;
    private final CountryService countryService;
    private final Mapper<City, CityDto> mapper = new Mapper<>(CityService::toDto);

    @Autowired
    public CityController(CityService cityService, CountryService countryService) {
        this.cityService = cityService;
        this.countryService = countryService;
    }

    @Operation(summary = "Id bazlı şehir bilgilerini getirir.")
    @GetMapping(value = "/{id}")
    public ResponseEntity<CityDto> findById(@PathVariable Long id) {
        City city = cityService.findById(id);
        CityDto dto = CityService.toDto(city);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Tüm şehirlere ait bilgileri getirir.")
    @GetMapping
    public ResponseEntity<PageDto<CityDto>> findAll(@RequestParam(required = false) Long countryId,
                                                    @PageableDefault(page = 0, size = 20)
                                                    @SortDefault.SortDefaults({
                                                            @SortDefault(sort = "id", direction = Sort.Direction.ASC)
                                                    })    Pageable pageable) {
        return ResponseEntity.ok(mapper.map(cityService.findAll(pageable)));
    }

    @Operation(summary = "Yeni bir şehir bilgisi eklemek için kullanılır.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CityResponse> create(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                               @Valid @RequestBody CreateCityDto request) throws CountryNotFoundException {
        City city = cityService.save(request, authenticatedUserId);
        return ResponseEntity.status(HttpStatus.CREATED).body(CityResponse.builder().id(city.getId()).build());

    }

    @Operation(summary = "İd bazlı şehir bilgisi güncellemek için kullanılır.")
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CityResponse> replace(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                                @PathVariable Long id,
                                                @Valid @RequestBody CreateCityDto request) throws CountryNotFoundException {
        City city = cityService.replace(id, request, authenticatedUserId);
        return ResponseEntity.status(HttpStatus.CREATED).body(CityResponse.builder().id(city.getId()).build());
    }

    @Operation(summary = "İd bazlı şehir bilgisi silmek için kullanılır.")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                         @PathVariable Long id) {
        cityService.deleteById(id);
        return ResponseEntity.ok("City deleted.");

    }
}
