select * from student;

select * from faculty;

select name, age from student
where age > 30 and age < 40;

select name from student;

select * from student
where name ilike '%a%';

select * from student
where age < 37;

select * from student
order by age;

select name, age from student
group by name, age;

select * from student, faculty;



