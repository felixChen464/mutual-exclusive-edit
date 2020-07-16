package com.ringcentral.assessment.protocol;

import lombok.Data;

public interface FileProtocol {

    interface SaveFile{

        @Data
        class Input{

            /**
             * 文件名
             */
            private String fileName;

            /**
             * 文本内容
             */
            private String content;
        }

    }

    interface Lock{

        @Data
        class Input{

            /**
             * 发送编辑请求的用户id
             */
            private String userId;

            /**
             * 文件id
             */
            private String fileId;
        }

    }

    interface EditFile{

        @Data
        class Input{

            /**
             * 发送编辑请求的用户id
             */
            private String userId;

            /**
             * 文件id
             */
            private String fileId;

            /**
             * 文件名
             */
            private String fileName;

            /**
             * 文本内容
             */
            private String content;
        }

    }
}
