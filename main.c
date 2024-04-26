#include <msp430x16x.h>
#include "Config.h"

unsigned char key,led;

#pragma vector=PORT1_VECTOR
__interrupt void Port_1 (void)
{
  delay_ms(20);
  if(!(P1IN & 0x01))
  {
    while(!(P1IN & 0x01));
    delay_ms(20);
    key = 1;
  }
  if(!(P1IN & 0x02))
  {
    while(!(P1IN & 0x02));
    delay_ms(20);
    key = 2;
  }
  P1IFG = 0x00;
}

//***********************************************************************
//      主程序
//***********************************************************************
void main(void)
{ 
  WDT_Init();
  Clock_Init();                       //时钟初始化
  P6DIR = 0xff;
  P1DIR &= 0xfc;
  P6OUT = 0xff;                       //灭灯
  P1IE |= 0x03;
  P1IES |= 0x03;
  P1IFG = 0x00;
  delay_ms(100);
  _EINT();                            //初始化
  key = 0x00;
  led = 0x01;
  while(1)
  {
    if(key == 1)
    {
      led=led<<1;
      if(led == 0)
      {
        led = 0x01;
      }
    }
    if(key == 2)
    {
      led=led>>1;
      if(led == 0)
      {
        led = 0x80;
      }
    }
    P6OUT = ~led;
    delay_ms(200);
  }
}



