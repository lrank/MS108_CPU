module ALU(
  src1,
  src2,
  op,
  result
  );

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

input wire [2:0] op;
input wire [31:0] src1;
input wire [31:0] src2;
output reg [31:0] result;

always @(src1 or src2 or op)
begin
  case (op)
    OP_ADD: result = src1 + src2;
    OP_MUL: result = src1 * src2;
    OP_BGE: result = src1 - src2;
    OP_SLL: result = src1 << src2;
  endcase
end

endmodule