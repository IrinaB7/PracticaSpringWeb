package org.eduardomango.practicaspringweb.model.services;

import org.eduardomango.practicaspringweb.model.entities.ProductEntity;
import org.eduardomango.practicaspringweb.model.entities.SaleEntity;
import org.eduardomango.practicaspringweb.model.entities.UserEntity;
import org.eduardomango.practicaspringweb.model.exceptions.SaleNotFoundException;
import org.eduardomango.practicaspringweb.model.repositories.IRepository;
import org.eduardomango.practicaspringweb.model.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService {
    private final IRepository<SaleEntity> saleEntityIRepository; //crea un atributo de tipo repository
                                                                 // y este trae los metodos que tiene la interfaz
    private final ProductService productService;
    private final UserService userService;

    //constructor del atributo
    public SaleService(IRepository<SaleEntity> saleEntityIRepository, ProductService productService, UserService userService) {
        this.saleEntityIRepository = saleEntityIRepository;
        this.productService = productService;
        this.userService = userService;
    }

    //enlistar ventas
    public List<SaleEntity> enlistarSale (){
        return  saleEntityIRepository.findAll(); //hace una lista (la toma del repository y la hace de tipo sale entity)
    }

    //registrar una venta
    public void registrarSale (long idVenta,long idProducto, long idCliente, long cantidad){
        ProductEntity aux = productService.findById(idProducto);
        UserEntity auxUser = userService.findById(idCliente);

        SaleEntity nuevaVenta = SaleEntity.builder()
                .products(aux)
                .client(auxUser)
                .quantity(cantidad)
                .build();

        saleEntityIRepository.save(nuevaVenta);

    }

    //buscar venta por id
    public SaleEntity buscarVenta (long id){
        return saleEntityIRepository.findAll()
                .stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElseThrow(SaleNotFoundException::new);
    }

    //eliminar una venta
    public void eliminarSale (long idVenta){
        SaleEntity aux = buscarVenta(idVenta);
        saleEntityIRepository.delete(aux);
    }

    //actualizar una venta
    public void actualizarSale (long id, long idProducto, long idCliente, long cantidad){
        SaleEntity auxVenta = buscarVenta(id);
        ProductEntity auxProducto = productService.findById(idProducto);
        UserEntity auxUser = userService.findById(idCliente);

        auxVenta.setClient(auxUser);
        auxVenta.setProducts(auxProducto);
        auxVenta.setQuantity(cantidad);

        saleEntityIRepository.update(auxVenta);
    }

}
