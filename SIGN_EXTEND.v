module SIGN_EXTEND(
  IR_i,
  data_o
);

input wire [31:0] IR_i;
output reg [31:0] data_o;

`include "parameter.v"

always
begin
  assign data_o = IR_i;
  if (IR_i[31:28] == ALU_J)
    assign data_o={data_o[27], data_o[27], data_o[27], data_o[27],
            data_o[27:0]};
  else
    if (IR_i[31:28] == ALU_LI)
      assign data_o={data_o[22], data_o[22], data_o[22], data_o[22],
        data_o[22], data_o[22], data_o[22], data_o[22], data_o[22],
        data_o[22:0]};
    else
    assign data_o={data_o[17], data_o[17], data_o[17], data_o[17],
      data_o[17], data_o[17], data_o[17], data_o[17], data_o[17],
      data_o[17], data_o[17], data_o[17], data_o[17], data_o[17],
            data_o[17:0]};

end

endmodule
