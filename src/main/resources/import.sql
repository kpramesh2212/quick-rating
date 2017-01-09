insert into product(product_id, product_name) VALUES (1, 'Product1');
insert into product(product_id, product_name) VALUES (2, 'Product2');
insert into product(product_id, product_name) VALUES (3, 'Product3');
insert into product(product_id, product_name) VALUES (4, 'Product4');

insert into criterion(CRITERION_ID, CRITERION_NAME, WEIGHT) VALUES (1, 'Criterion1', 10);
insert into criterion(CRITERION_ID, CRITERION_NAME, WEIGHT) VALUES (2, 'Criterion2', 20);
insert into criterion(CRITERION_ID, CRITERION_NAME, WEIGHT) VALUES (3, 'Criterion3', 30);
insert into criterion(CRITERION_ID, CRITERION_NAME, WEIGHT) VALUES (4, 'Criterion4', 40);

insert into project(project_id, project_name) values (1, 'Project 1');
insert into project_products values (1, 1);
insert into project_products values (1, 2);
insert into project_criteria values (1, 1);
insert into project_criteria values (1, 2);


insert into project(project_id, project_name) values (2, 'Project 2');
insert into project_products values (2, 3);
insert into project_products values (2, 4);
insert into project_criteria values (2, 3);
insert into project_criteria values (2, 4);