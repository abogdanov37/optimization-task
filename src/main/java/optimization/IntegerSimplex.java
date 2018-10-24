package optimization;

import java.util.Arrays;
import java.util.OptionalDouble;

/**
 * Created by abogdanov on 29.12.2017.
 */
public class IntegerSimplex {


    public void getLimitations(double[] solution) {
        OptionalDouble maxValue = Arrays.stream(solution).map(x -> x % 1).max();


    }

    /*procedure TForm1.Button3Click(Sender: TObject);
    label
            fin,finals;
    var
    z,f1,f2,f3,f4,s,d:real;
    mas_of_zna:array[1..10,1..10]of real;
    mas_of_min:array[1..10]of real;
    max_drob_n,kluch_stolb,kluch_strok:Integer;
    mas_of_q:array[1..10]of real;
    q,max,max_drob_z,x,min2,m,min1:real;
    begin
    max:=-9999;

//
for i:=2 to REshenie.rowCount-2 do
    If (frac(StrToFloat(REshenie.Cells[REshenie.colcount-1,i]))<>0) and  (frac(StrToFloat( REshenie.Cells[REshenie.ColCount-1,i]))>max) then
            begin
    max:=frac(StrToFloat(REshenie.Cells[REshenie.colcount-1,i]));
    max_drob_n:=i;
    max_drob_z:=StrToFloat(REshenie.Cells[REshenie.colcount-1,i]);

    end;
    finals:
//Проверка на наличие дроби
            if (max_drob_z<>0) or (frac(StrToFloat(REshenie.Cells[REshenie.ColCount-1,REshenie.RowCount-1]))<>0)then
            begin
    Label5.Caption:=IntToStr(REshenie.ColCount-1);
    Label6.Caption:=IntToStr(max_drob_n);


//Нахождение элементов дополнительного ограничения
    mas_of_q[REshenie.ColCount]:=StrToFloat(REshenie.Cells[REshenie.ColCount-1,max_drob_n])-trunc(StrToFloat(REshenie.Cells[REshenie.ColCount-1,max_drob_n]));
    mas_of_q[REshenie.ColCount-1]:=1;
for i:=2 to REshenie.ColCount-2 do
    begin

if (StrToFloat(REshenie.Cells[i,max_drob_n])<0)  and (frac(StrToFloat(REshenie.Cells[i,max_drob_n]))<>0)  then
    mas_of_q[i]:=StrToFloat(REshenie.Cells[i,max_drob_n])-Trunc(StrToFloat(REshenie.Cells[i,max_drob_n])-1)
            else
    mas_of_q[i]:=StrToFloat(REshenie.Cells[i,max_drob_n])-Trunc(StrToFloat(REshenie.Cells[i,max_drob_n]));
    end;
//вывод ключевых
for i:=2 to REshenie.ColCount do
    begin
    Q_str.Cells[i-2,0]:=floattostr( mas_of_q[i]);
    end;
//добавление новой строки
    REshenie.RowCount:=REshenie.RowCount+1;

//перенос оценок на последнюю строчку
for i:=2 to REshenie.ColCount do
    begin
    REshenie.Cells[i,REshenie.RowCount-1]:=REshenie.Cells[i,REshenie.RowCount-2];
    REshenie.Cells[i,REshenie.RowCount-2]:='';
    end;
    REshenie.Cells[1,REshenie.RowCount-2]:='X'+IntToStr(strtoint(REshenie.Cells[REshenie.colcount-2,1][2])+1);
    REshenie.Cells[0,REshenie.RowCount-2]:='0';
//добавление нового столбца
    REshenie.ColCount:=REshenie.COlCount+1;
//перенос  на последнюю столбец
for i:=2 to REshenie.rowCount do
    begin
    REshenie.Cells[REshenie.COlCount-1,i]:=REshenie.Cells[REshenie.colcount-2,i];
    REshenie.Cells[REshenie.COlCount-2,i]:='';
    end;
//добавление Значиние кооцифецента
    REshenie.Cells[REshenie.ColCount-2,1]:=REshenie.Cells[1,REshenie.Rowcount-2];
    REshenie.Cells[REshenie.ColCount-2,0]:='0';
//Заполнение 0
for i:=2 to REshenie.RowCount-1 do
    begin
    REshenie.Cells[REshenie.ColCount-2,i]:='0';
    end;
//Заполнение строки q1
for i:=2 to REshenie.ColCount-1 do
    begin
if (mas_of_q[i]<>0) and (mas_of_q[i]<>1) then
    REshenie.Cells[i,REshenie.RowCount-2]:='-'+FloatToStr(mas_of_q[i])
else
    REshenie.Cells[i,REshenie.RowCount-2]:=FloatToStr(mas_of_q[i]);
    end;

//Знак - или +
for  i:=2 to REshenie.ColCount-3 do
    begin
if (RadioGroup1.ItemIndex=1) and   (REshenie.Cells[i,reshenie.rowcount-1][1]<>'-') and (StrToFloat(REshenie.Cells[i,reshenie.rowcount-1])>0) then
    REshenie.Cells[i,reshenie.rowcount-1]:='-'+REshenie.Cells[i,reshenie.rowcount-1];
    end;
    min1:=99;

//Нахождение клю столбца для гомори
for i:=1 to REshenie.ColCount-3 do
    begin
if (StrToFloat(REshenie.Cells[i+1,REshenie.rowcount-2]) <>0) then
if  StrToFloat(REshenie.Cells[i+1,REshenie.rowcount-1])<>0  then
if (StrToFloat(REshenie.Cells[i+1,REshenie.rowcount-1])/StrToFloat(REshenie.Cells[i+1,REshenie.rowcount-2])   <min1 ) and (StrToFloat(REshenie.Cells[i+1,REshenie.rowcount-1]) <>0)  then
            begin
    min1:=StrToFloat(REshenie.Cells[i+1,REshenie.rowcount-1])/StrToFloat(REshenie.Cells[i+1,REshenie.rowcount-2]);
    kluch_stolb:=i+1;
    end;

    end;


for i:=1 to REshenie.RowCount-3 do
    begin
if REshenie.Cells[kluch_stolb,i+1]<>'0' then
    mas_of_min[i]:=strtofloat(REshenie.Cells[REshenie.Colcount-1,i+1]) /strtofloat(REshenie.Cells[kluch_stolb,i+1])
else
    mas_of_min[i]:=0;
    end;

//Поиск минимальной строки
    min2:=9999;
for i:=1 to REshenie.RowCount-3 do
            if (mas_of_min[i]<min2) and (mas_of_min[i]>0) then
    begin
    min2:=mas_of_min[i];
    kluch_strok:=i+1;
    end;
    f4:=StrToFloat(REshenie.Cells[kluch_stolb,kluch_strok]);



//Замена базиса
    REshenie.Cells[0,kluch_strok]:=REshenie.Cells[kluch_stolb,0];
    REshenie.Cells[1,kluch_strok]:=REshenie.Cells[kluch_stolb,1];

//Правило прямоугольника
for i:=2 to REshenie.RowCount-2 do
    begin
for j:=2 to REshenie.ColCount-1 do
    begin
if i<>kluch_strok then
if j<>kluch_stolb then
            begin
    f1:=StrToFloat(REshenie.Cells[j,i]);
    f2:=StrToFloat(REshenie.Cells[j,kluch_strok]);
    f3:=StrToFloat(REshenie.Cells[kluch_stolb,i]);
    mas_of_zna[j,i]:=((f1*f4)-(f2*f3))/f4;
    end;
    end;
    end;

//Нули в столбце
for i:=2 to REshenie.RowCount-1 do
    begin
    REshenie.Cells[kluch_stolb,i]:='0';
    end;
    REshenie.Cells[kluch_stolb,kluch_strok]:=floattostr(f4);
//Строка делится на ключевой элемент
for i:=2 to REshenie.ColCount-1 do
    begin
    REshenie.Cells[i,kluch_strok]:=floattostr(strtofloat(REshenie.Cells[i,kluch_strok])/f4);
    end;
    z:=StrToFloat(REshenie.Cells[kluch_stolb,kluch_strok]);
for i:=2 to REshenie.RowCount-2 do
    begin
for j:=2 to REshenie.ColCount-1 do
    begin
if i<>kluch_strok then
if j<>kluch_stolb then
            begin
    REshenie.Cells[j,i]:=FloatToStr(mas_of_zna[j,i]);
    end;
    end;
    end;

//Подсчет оценок
for i:=2 to REshenie.ColCount-1 do
    begin
for j:=2 to REshenie.RowCount-2 do
    begin
    m:=StrTofloat( REshenie.cells[i,j])*StrTofloat(REshenie.Cells[0,j]);
    x:=x+m;
    end;
if i=REshenie.ColCount-1 then
    REshenie.Cells[i,REshenie.RowCount-1]:=FloatToStr(x)
else
    REshenie.Cells[i,REshenie.RowCount-1]:=FloatToStr(x-StrTofloat(REshenie.Cells[i,0]));
    x:=0;
    end;




//Округление

for j:=2 to REshenie.RowCount-1 do
    begin
if ((REshenie.Cells[REshenie.Colcount-1,j][3]='0') and (REshenie.Cells[REshenie.Colcount-1,j][4]='0') and (REshenie.Cells[REshenie.Colcount-1,j][5]='0'))
    or ((REshenie.Cells[REshenie.Colcount-1,j][1]='-') and (REshenie.Cells[REshenie.Colcount-1,j][4]='0') and (REshenie.Cells[REshenie.Colcount-1,j][5]='0') and (REshenie.Cells[REshenie.Colcount-1,j][6]='9'))
    or ((REshenie.Cells[REshenie.Colcount-1,j][3]='9') and (REshenie.Cells[REshenie.Colcount-1,j][4]='9') and (REshenie.Cells[REshenie.Colcount-1,j][5]='9'))
    or ((REshenie.Cells[REshenie.Colcount-1,j][1]='-') and (REshenie.Cells[REshenie.Colcount-1,j][4]='9') and (REshenie.Cells[REshenie.Colcount-1,j][5]='9') )
    then
    REshenie.Cells[REshenie.Colcount-1,j]:=inttostr(round(StrToFloat(REshenie.Cells[REshenie.Colcount-1,j])));
    end;




    Label5.Caption:=FloatToStr(kluch_stolb);
    Label6.Caption:=FloatToStr(kluch_strok);

    end
else
    begin

    MessageDlg('Дробей в решении нет, следовательно ответ целочисленный',mtWarning,[mbOK],1);
    Button3.Visible:=false;
for i:=1 to Q_str.ColCount-1 do
    Q_str.Cells[i,0]:='';
    For i:=1 to SpinEdit1.Value do
    begin
    Q_str.Cells[i-1,0]:='X'+IntToStr(i);
    end;
for i:=2 to REshenie.RowCount-2 do
            for j:=1 to SpinEdit1.Value do
            if REshenie.Cells[1,i]='X'+IntToStr(j) then
    Q_str.Cells[j-1,1]:=REshenie.Cells[REshenie.colcount-1,i];
    Label12.Caption:=REshenie.Cells[REshenie.colcount-1,REshenie.RowCount-1];
    end;







    end;*/
}
