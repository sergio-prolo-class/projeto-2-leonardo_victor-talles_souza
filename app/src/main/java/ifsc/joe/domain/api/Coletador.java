package ifsc.joe.domain.api;

import ifsc.joe.domain.impl.CriarRecurso;
import ifsc.joe.enums.Recurso;

public interface Coletador {
    Recurso coletar(CriarRecurso p);
}
