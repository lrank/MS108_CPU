module hazard(
  clk_i,
  ir1_i, //IF/ID
  ir2_i, //ID/EX
  ir3_i, //EX/MEM
  ir4_i,
  isstall1_o, //0 for no stall, 1 for stall, //pc
  isstall2_o, // IF/ID
  control_o   // to MUX4
  );

input wire clk_i;
input wire [31:0] ir1_i;
input wire [31:0] ir2_i;
input wire [31:0] ir3_i;
input wire [31:0] ir4_i;
output reg isstall1_o;
output reg isstall2_o;
output reg control_o;

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

reg [4:0] w;
reg [5:0] s1, s2; //first digit 0 for r

reg [1:0] left;
initial
begin
  left = 2'b0;
end

always @(/*ir1_i or ir2_i or ir3_i*/negedge clk_i)
begin
  /*
  if (ir2_i[31:28] == ALU_BGE ||
      ir2_i[31:28] == ALU_J ||
      ir3_i[31:28] == ALU_BGE ||
      ir3_i[31:28] == ALU_J/* ||
      ir4_i[31:28] == ALU_BGE ||
      ir4_i[31:28] == ALU_J) begin
        isstall1_o = 1'b1;
        isstall2_o = 1'b0;
        control_o = 1'b1;
      end*/
  
  if (ir2_i[31:28] == ALU_BGE ||
      ir2_i[31:28] == ALU_J)
      begin
        left = 2'b10;
      end
  else begin
    if (ir2_i[31:28] == ALU_LW ||
        ir2_i[31:28] == ALU_LI ||
        ir2_i[31:28] == ALU_ADDU ||
        ir2_i[31:28] == ALU_ADDIU ||
        ir2_i[31:28] == ALU_SLL ||
        ir2_i[31:28] == ALU_MUL ||
        ir2_i[31:28] == ALU_MULI)
    begin
      w = ir2_i[27:23];
      s1=6'b111111;
      s2=6'b111111;
      case (ir1_i[31:28])
        ALU_LW: fork s1 = {1'b0, ir1_i[22:18]}; s2=6'b111111; join
        ALU_SW: fork s1 = {1'b0, ir1_i[27:23]}; s2 = {1'b0, ir1_i[22:18]}; join
        ALU_ADDU: fork s1 = {1'b0, ir1_i[22:18]}; s2 = {1'b0, ir1_i[17:13]}; join
        ALU_ADDIU: fork s1 = {1'b0, ir1_i[22:18]}; s2=6'b111111; join
        ALU_SLL: fork s1 = {1'b0, ir1_i[22:18]}; s2=6'b111111; join
        ALU_MUL: fork s1 = {1'b0, ir1_i[22:18]}; s2 = {1'b0, ir1_i[17:13]}; join
        ALU_BGE: fork s1 = {1'b0, ir1_i[27:23]}; s2 = {1'b0, ir1_i[22:18]}; join
        ALU_MULI: fork s1 = {1'b0, ir1_i[22:18]}; s2=6'b111111; join
      endcase
      
      if ((s1[5] == 1'b0 && s1[4:0] == w) ||
          (s2[5] == 1'b0 && s2[4:0] == w))
        begin
          isstall1_o = 1'b1;
          isstall2_o = 1'b0;
          control_o = 1'b1;
        end
		  else
		    begin
			   isstall1_o = 1'b0;
			   isstall2_o = 1'b0;
			   control_o = 1'b0;
		    end
    end
    else
      begin
        isstall1_o = 1'b0;
        isstall2_o = 1'b0;
        control_o = 1'b0;
      end
  end
  
  if (left != 2'b0)
    begin
      isstall1_o = 1'b1;
      isstall2_o = 1'b0;
      control_o = 1'b1;
      left = left - 2'b01;
    end  
end

endmodule