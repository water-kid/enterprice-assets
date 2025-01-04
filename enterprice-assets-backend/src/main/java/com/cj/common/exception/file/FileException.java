package com.cj.common.exception.file;

import com.cj.common.exception.base.BaseException;

/**
 * 文件信息异常类
 * 
 * @author commendation
 */
public class FileException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args)
    {
        super("file", code, args, null);
    }

}
