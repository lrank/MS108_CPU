module Regis(
  IR_i,
  writenum_i,
  writedata_i,
  data1_o,
  data2_o,
  );

input wire [31:0] IR_i;
input wire [5:0]  writenum_i;
input wire [31:0] writedata_i;
output reg [31:0] data1_o;
output reg [31:0] data2_o;

reg [31:0] value [0:31];

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
  /*reg[4:0] t = 5'd0;
  for (i=18;i<=22;i=i+1)
      t=t*2+IR_i[i];*/
    if (writenum_i[5] == 1'b1) //1 for write
      value[writenum_i[4:0]] = writedata_i;
      
    if (IR_i[31:28] == ALU_ADDU ||
        IR_i[31:28] == ALU_MUL)
      begin
        assign data1_o = value[IR_i[22:18]];
        assign data2_o = value[IR_i[17:13]];
      end
    else begin
      assign data1_o = value[IR_i[22:18]];
      assign data2_o = value[IR_i[27:23]];
    end
end

endmodule
