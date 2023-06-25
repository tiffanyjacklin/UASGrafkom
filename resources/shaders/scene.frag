#version 330

// Directional Light
struct DirLight {
    vec3 direction;
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
};
uniform DirLight dirLight;

struct PointLight{
    vec3 position;
    float constant;
    float linear;
    float quadratic;

    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
};

#define NR_POINT_LIGHTS 6
uniform PointLight pointLights[NR_POINT_LIGHTS];

struct SpotLight{
    vec3 position;
    vec3 direction;

    float cutOff;
    float outerCutOff;

    vec3 ambient;
    vec3 diffuse;
    vec3 specular;

    float constant;
    float linear;
    float quadratic;
};

uniform SpotLight spotLight;

uniform vec4 uni_color;
out vec4 fragColor;

//uniform vec3 lightColor;
//uniform vec3 lightPos;
uniform vec3 viewPos;

in vec3 Normal;
in vec3 FragPos;

vec3 CalcDirLight(DirLight light, vec3 normal, vec3 viewDir){
    vec3 lightDir = normalize(-light.direction);

    //diffuse
    float diff = max(dot(normal,lightDir),0.0);
    //specular
    vec3 reflectDir = reflect(-lightDir,normal);
    float spec = pow(max(dot(viewDir, reflectDir),0.0),64);

    vec3 ambient = light.ambient;
    vec3 diffuse = light.diffuse * diff;
    vec3 specular = light.specular * spec;
    return(ambient + diffuse + specular);
}

vec3 CalcPointLight(PointLight light, vec3 normal, vec3 fragPos, vec3 viewDir)
{
    vec3 lightDir = normalize(light.position - fragPos);
    //diffuse shading
    float diff = max(dot(normal, lightDir), 0.0);
    //specular shading
    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0),3072);
    //attenuation
    float distance    = length(light.position - fragPos);
    float attenuation = 1.0 / (light.constant + light.linear * distance +
    light.quadratic * (distance * distance));
    //combine results
    vec3 ambient  = light.ambient;
    vec3 diffuse  = light.diffuse  * diff;
    vec3 specular = light.specular * spec;
    ambient  *= attenuation;
    diffuse  *= attenuation;
    specular *= attenuation;
    return (ambient + diffuse + specular);
}

vec3 CalcSpotLight(SpotLight light, vec3 normal, vec3 fragPos, vec3 viewDir)
{

    //diffuse shading
    vec3 lightDir = normalize(light.position - FragPos);
    float diff = max(dot(normal, lightDir), 0.0);

    //specular shading
    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0),3072);

    //attenuation
    float distance    = length(light.position - FragPos);
    float attenuation = 1.0 / (light.constant + light.linear * distance +
    light.quadratic * (distance * distance));

    //spotlight intensity
    float theta     = dot(lightDir, normalize(-light.direction));
    float epsilon   = light.cutOff - light.outerCutOff;
//    float intensity = clamp((theta - light.outerCutOff) / epsilon, 0.0, 1.0);
    float intensity = smoothstep(light.outerCutOff, light.cutOff, theta);

    //combine results
    vec3 ambient = light.ambient;
    vec3 diffuse = light.diffuse * diff * intensity;
    vec3 specular = light.specular * spec * intensity;
    ambient  *= attenuation;
    diffuse  *= attenuation;
    specular *= attenuation;
    return (ambient + diffuse + specular);

}

void main()
{
    // Properties
    vec3 normal = normalize(Normal);
    vec3 viewDir = normalize(viewPos - FragPos);

    // Directional Light
    vec3 result = CalcDirLight(dirLight, normal, viewDir);

    // Point Lights
    for (int i = 0; i <NR_POINT_LIGHTS; i++){
        result += CalcPointLight(pointLights[i], normal, FragPos, viewDir);
    }

    // SpotLight
    result += CalcSpotLight(spotLight, normal, FragPos, viewDir);

    fragColor = vec4(result * vec3(uni_color),1.0);


//    //ambient (strength e 0.1 - 0.5 krn tujuan e ngasi warna ke area yg g kena cahaya, nek lbh apa bedae ntik sm yg kena cahaya)
//    float ambientStrength = 0.1f;
//    vec3 ambient = ambientStrength * lightColor;
//    //diffuse
//    vec3 lightDirection = normalize(lightPos - FragPos);
//    float diff = max(dot(Normal, lightDirection), 0.0); // kalo negatif diisi 0 jdne
//    vec3 diffuse = diff * lightColor;
//
//    // Specular
//    float specularStrength = 0.5f;
//    vec3 viewDir = normalize(viewPos - FragPos);
//
//    // blinn phong
//    vec3 halfwayDir = normalize(lightDirection + viewDir);
//    float spec = pow(max(dot(Normal, halfwayDir),0.0),64*3);
//
//    //original phong
////    vec3 reflectDir = reflect(-lightDirection, Normal);
////    float spec = pow(max(dot(viewDir, reflectDir),0.0),64);
////
//    vec3 specular = specularStrength * spec *lightColor;
//
//    //vec4(red,green,blue,alpha)
//    //rgba -> red 100/255
////    fragColor = vec4(0.0,1.0,1.0,1.0);
////    fragColor = uni_color;
//    vec3 result = (ambient + diffuse + specular) * vec3(uni_color);
//
//    fragColor = vec4(result, 1.0);
}
