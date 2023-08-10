insert into usr(username, password, email)
values ('Admin', '$2a$10$JT1Ue/MmilHWsMo0NAIOieknXK6RWc8.gritzdBSLy/Osya2BVl66', 'Admin@mpei.ru');

insert into usr_roles(id_usr, id_role)
select u.id, r.id as role_name
from usr u
         inner join usr_roles ur on u.id = ur.id_usr
         inner join role r on ur.id_role = r.id
where u.username = 'Admin';