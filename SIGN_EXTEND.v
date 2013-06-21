module SIGN_EXTEND(
  IR_i,
  data_o
);

input wire [31:0] IR_i;
output reg [31:0] data_o;

//`include "parameter.v"
parameter NIB_SIZE = 4;
parameter BYTE_SIZE = 8;
parameter WORD_SIZE = 16;
parameter MEM_SIZE = 1024 * 4;

parameter ALU_LW =    4'b0000;
parameter ALU_SW =    4'b0001;
parameter ALU_LI =    4'b0010;
parameter ALU_ADDU =  4'b0011;
parameter ALU_ADDIU = 4'b0100;
parameter ALU_SLL =   4'b0101;
parameter ALU_MUL =   4'b0110;
parameter ALU_BGE =   4'b0111;
parameter ALU_J =     4'b1000;
parameter ALU_MULI =  4'b1001;

parameter OP_ADD = 3'b000,
          OP_MUL = 3'b001,
          OP_SLL = 3'b010,
          OP_BGE = 3'b011;

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
