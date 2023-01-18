insert into tipo_usuario(id,descripcion) values (1,'Cliente');
insert into tipo_usuario(id,descripcion) values (2,'Conductor');
insert into persona(documento,nombres,apellidos,correoelectronico,numerocontacto,tipo_usuario_id) values (12345,'Cliente','Prueba','pruebacliente@gmail.com',3201111111,1);
insert into persona(documento,nombres,apellidos,correoelectronico,numerocontacto,tipo_usuario_id) values (98765,'Conductor','Prueba','pruebaconductor@gmail.com',3202222222,2);
insert into vehiculo(id,descripcion,marca,modelo,placa,conductor_documento,disponible) values (1,'Camioneta color rojo','Mazda','2023','PLA123',98765,true);