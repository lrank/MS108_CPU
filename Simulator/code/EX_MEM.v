module EX_MEM(
  clk_i,
  data1_i,
  data2_i,
  IR_i,
  data1_o,
  data2_o,
  IR_o,
  control_o,
  row_o,
  control_MEM_o
);

input wire clk_i;
input wire [31:0] data1_i;
input wire [31:0] data2_i;
input wire [31:0] IR_i;
output reg [31:0] data1_o;
output reg [31:0] data2_o;
output reg [31:0] IR_o;
output reg control_o;
output reg [1:0]row_o; // 00 for nop, 01 for read, 10 for write
output reg control_MEM_o;

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

initial
fork
  control_o=1'b0;
join

always @(posedge clk_i)
begin
  IR_o = IR_i;
  data1_o = data1_i;
  data2_o = data2_i;
  
  if (IR_o[31:28] == ALU_J)
    control_o = 1'b1;
  else
    control_o = 1'b0;
  
  if (IR_o[31:28] == ALU_BGE)
    begin
      if (data1_i[31] == 1'b0) begin
        control_o = 1'b1;
        data1_o = data2_i;
      end
    else begin
      control_o = 1'b0;
      data1_o = 32'b0;
    end
    end
  
  if (IR_o[31:28] == ALU_LW)
    row_o = 2'b01;
  else if (IR_o[31:28] == ALU_SW)
    row_o = 2'b10;
  else row_o = 2'b0;
    
  
  if (IR_i[31:28] == ALU_LW)
  	control_MEM_o = 1'b0;
  else
  	control_MEM_o = 1'b1;
end
endmodule
