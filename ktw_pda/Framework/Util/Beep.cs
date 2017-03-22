using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using Symbol.Audio;
using Symbol.StandardForms;

namespace Framework
{
    /// <summary>
    /// 声音
    /// </summary>
    public class Beep
    {
        // Fields
        public static int ALARMSOUND = 1;
        public static int ERRORSOUND = 0;
        private static Controller MyAudioController;
        private static Device MyDevice;
        public static int[,] soundConfig = new int[,] { { 0x5dc, 0xa6e, 1 }, { 500, 0xa6e, 1 }, { 200, 0xa6e, 1 } };
        private static int soundType;
        public static int SUCCESSSOUND = 2;

        // Methods
        public static void dispose()
        {
            try
            {
                MyDevice = null;
                if (MyAudioController != null)
                {
                    MyAudioController.Dispose();
                }
                MyAudioController = null;
            }
            catch
            {
                //do nothing
            }
        }

        static Beep()
        {
#if !DEBUG
            MyDevice = (Device)SelectDevice.Select(Controller.Title, Device.AvailableDevices);
            MyAudioController = new StandardAudio(MyDevice);
#endif
        }

        private static void sound()
        {
            try
            {
#if !DEBUG
                if (soundType == 0)
                {
                    MyAudioController.PlayAudio(soundConfig[soundType, 0], soundConfig[soundType, 1]);
                }
                else if (soundType == 1)
                {
                    MyAudioController.PlayAudio(soundConfig[soundType, 0], soundConfig[soundType, 1]);
                }
                else if (soundType == 2)
                {
                    MyAudioController.PlayAudio(soundConfig[soundType, 0], soundConfig[soundType, 1]);
                }
#endif
            }
            catch (Exception)
            {
            }
        }

        public static bool soundBeep(int type)
        {
            soundType = type;
            sound();
            return true;
        }
    }
}
