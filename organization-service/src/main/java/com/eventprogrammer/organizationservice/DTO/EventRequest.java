package com.eventprogrammer.organizationservice.DTO;




import java.util.Date;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRequest {

    @NotBlank
    @NotEmpty
    @NotNull
    @Size(max = 50)
    private String nome;
    @NotBlank
    @NotEmpty
    @NotNull
    @Size(max = 50)
    private String tipologia;
    @NotBlank
    @NotEmpty
    @NotNull
    @Size(max = 50)
    private String indirizzo;
    @Future
    private Date dataEoraDate;
    @Min(1)
    private int maxPrenotati;
    
}
