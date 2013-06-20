module Register(
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

`include "parameter.v"

always
begin
  /*reg[4:0] t = 5'd0;
  for (i=18;i<=22;i=i+1)
      t=t*2+IR_i[i];*/
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
    if (writenum_i[5] == 1'b1) //1 for write
      value[writenum_i[4:0]] = writedata_i;
end

endmodule
