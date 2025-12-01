package ifsc.joe.domain.api;

import ifsc.joe.enums.Recurso;

public interface Coletador {
    boolean coletar(Recurso recurso);
}
