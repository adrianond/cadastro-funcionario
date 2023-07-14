alter table entidade add column ent_cpf character varying (11);

alter table entidade add constraint unique_constraint_cpf unique  (ent_cpf);
