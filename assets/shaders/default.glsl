#type vertex
#version 330 core

layout (location=0) in vec3 aPos;       // location 0 means the 1st input , vec3 means 3d vector -> x, y, z
layout (location=1) in vec4 aColor;     // location 1 means 2nd input, vec4 means 4d vector -> rgba
out vec4 fColor;                        // this will be the 4d vector output from vertex shader as input to fragment shader


void main()
{
    fColor = aColor;
    gl_Position = vec4(aPos, 1.0);      // gl_position is the final position of the particular vertex on screen
}


#type fragment
#version 330 core

in vec4 fColor;                         // this is the output from the vertex shader
out vec4 color;                         // this is the final color values of the pixel (interpolated)


void main()
{
    color = fColor;
}